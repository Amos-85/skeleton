import sbt._
import sbt.Keys.dependencyOverrides

object Dependencies {

  private val AkkaHttpVersion = "10.1.11"
//  private val KafkaStreamsTestVersion = "1.0.4"
  private val AlpakkaS3Version = "2.0.0"
  private val AkkaHttpPlayJsonVersion = "1.27.0"
  private val AmazonS3SdkVersion = "1.11.789"

  val Common: Seq[ModuleID] = Seq(
//    "com.lightbend.akka" %% "akka-stream-alpakka-s3" % AlpakkaS3Version,
//    "com.amazonaws" % "aws-java-sdk-s3" % AmazonS3SdkVersion //exclude("commons-logging","commons-logging")
  )

  val package1: Seq[ModuleID] = Seq(
//    "io.swagger" %% "swagger-play2" % "1.7.1",
//    "org.webjars" % "swagger-ui" % "3.17.1"
  )

  val package2: Seq[ModuleID] = Seq(
//    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
//    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
//    "de.heikoseeberger" %% "akka-http-play-json" % AkkaHttpPlayJsonVersion
//    "com.typesafe.akka" %% "akka-stream-kafka-testkit" % KafkaStreamsTestVersion
  )

  dependencyOverrides ++= Seq (
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.0",
//    "org.scala-lang.modules" % "scala-xml" % "1.1.0",
    "commons-logging" % "commons-logging" % "1.1.3"
  )
}