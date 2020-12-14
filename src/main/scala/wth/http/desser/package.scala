package wth.http

import io.circe.generic.auto._
import io.circe._
import org.http4s._
import wth.model.anki.deck._
import wth.model.anki.note._
import wth.model.http.CirceEntitySerDes
import wth.model.pons.Response
import zio._
import zio.interop.catz._

package object desser {
  val responsesSerDer: ULayer[Has[CirceEntitySerDes[Seq[Response]]]] =
    ZLayer.succeed {
      new CirceEntitySerDes[Seq[Response]] {
        import io.circe.generic.auto._
        import zio.interop.catz._

        implicit def decoderCirce(implicit decoder: Decoder[Seq[Response]]): EntityDecoder[Task, Seq[Response]] =
          circe.jsonOf[Task, Seq[Response]]

        implicit def encoderCirce(implicit decoder: Encoder[Seq[Response]]): EntityEncoder[Task, Seq[Response]] =
          circe.jsonEncoderOf[Task, Seq[Response]]

        implicit override def encoder: EntityEncoder[Task, Seq[Response]] = encoderCirce

        implicit override def decoder: EntityDecoder[Task, Seq[Response]] = decoderCirce
      }
    }

  val createDeckSerDer: ULayer[Has[CirceEntitySerDes[CreateDeck]]] =
    ZLayer.succeed {
      new CirceEntitySerDes[CreateDeck] {

        implicit def decoderCirce(implicit decoder: Decoder[CreateDeck]): EntityDecoder[Task, CreateDeck] =
          circe.jsonOf[Task, CreateDeck]

        implicit def encoderCirce(implicit decoder: Encoder[CreateDeck]): EntityEncoder[Task, CreateDeck] =
          circe.jsonEncoderOf[Task, CreateDeck]

        implicit override def encoder: EntityEncoder[Task, CreateDeck] = encoderCirce

        implicit override def decoder: EntityDecoder[Task, CreateDeck] = decoderCirce
      }
    }

  val addNoteSerDer: ULayer[Has[CirceEntitySerDes[AddNote]]] =
    ZLayer.succeed {
      new CirceEntitySerDes[AddNote] {

        implicit def decoderCirce(implicit decoder: Decoder[AddNote]): EntityDecoder[Task, AddNote] =
          circe.jsonOf[Task, AddNote]

        implicit def encoderCirce(implicit decoder: Encoder[AddNote]): EntityEncoder[Task, AddNote] =
          circe.jsonEncoderOf[Task, AddNote]

        implicit override def encoder: EntityEncoder[Task, AddNote] = encoderCirce

        implicit override def decoder: EntityDecoder[Task, AddNote] = decoderCirce
      }
    }
}
