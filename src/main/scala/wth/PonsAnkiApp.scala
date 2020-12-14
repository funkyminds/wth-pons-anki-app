package wth

import com.typesafe.config.ConfigFactory
import org.http4s.client.Client
import wth.http.desser._
import wth.model._
import wth.model.http.CirceEntitySerDes
import wth.model.pons.Response
import wth.service._
import wth.service.files.FileBasedPhrasesProvider
import wth.service.http._
import zio._
import zio.config._
import zio.config.magnolia.DeriveConfigDescriptor
import zio.config.syntax._
import zio.config.typesafe.TypesafeConfig

object PonsAnkiApp extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    for {
      client <- Http4s.makeManagedHttpClient
      _ <- makeProgram(client)
    } yield ()
  }.exitCode

  private def makeProgram(client: TaskManaged[Client[Task]]) =
    Application
      .program[Seq[Response]]
      .provideSomeLayer[ZEnv] {
        resolveProgram(client)
      }

  private def resolveProgram(http4sClient: TaskManaged[Client[Task]]) = {
    val config = TypesafeConfig.fromTypesafeConfig(
      ConfigFactory.load(),
      DeriveConfigDescriptor.descriptor[Config]
    )

    val http4sLayer = http4sClient.toLayer.orDie
    val phrasesProvider = wordsProviderLayer(config)
    val queryLayer = createQueryLayer(config, http4sClient)
    val parserLayer = PonsResponseParser.service
    val repoLayer = createRepositoryLayer(config, http4sLayer)

    config ++ phrasesProvider ++ queryLayer ++ parserLayer ++ repoLayer
  }

  private def wordsProviderLayer(config: Layer[ReadError[String], ZConfig[Config]]) = {
    val wordsPath = config.narrow(_.words_path)
    wordsPath >>> FileBasedPhrasesProvider.service
  }

  private def createRepositoryLayer(config: Layer[ReadError[String], ZConfig[Config]],
                                    http4sLayer: ULayer[Has[Client[Task]]]) = {
    val httpsLayer = http4sLayer >>> Http4s.http4s
    val ankiCfg = config.narrow(_.anki)

    (addNoteSerDer ++ createDeckSerDer ++ ankiCfg ++ httpsLayer) >>> AnkiRestRepo.service[CirceEntitySerDes]
  }

  private def createQueryLayer(config: Layer[ReadError[String], ZConfig[Config]],
                               http4sClient: TaskManaged[Client[Task]]) = {
    val http4sLayer = http4sClient.toLayer.orDie
    val httpsLayer = http4sLayer >>> Http4s.http4s
    val ponsCfg = config.narrow(_.pons)

    (responsesSerDer ++ ponsCfg ++ httpsLayer) >>> PonsQuery.restApiService[Seq[Response], CirceEntitySerDes]
  }
}
