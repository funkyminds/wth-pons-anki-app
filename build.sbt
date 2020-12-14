name := "wth-pons-anki-app"

version := "0.0.1"

organization in ThisBuild := "io.funkyminds"

scalaVersion := "2.13.4"

scalacOptions := Seq("-unchecked", "-deprecation")

//@formatter:off
libraryDependencies ++= Seq(
  "io.funkyminds"       %%  "wth-anki"            % "0.0.1",
  "io.funkyminds"       %%  "wth-pons"            % "0.0.1",
  "io.funkyminds"       %%  "wth-http4s"          % "0.0.1",
  "dev.zio"             %%  "zio"                 % "1.0.3"
)
//@formatter:on
