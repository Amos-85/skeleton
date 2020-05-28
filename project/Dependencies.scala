import sbt._
import play.sbt.PlayImport.guice

object Dependencies {

  private val AkkaHttpVersion = "10.1.12"
  private val AkkaVersion = "2.5.31"
  private val KafkaStreamsTestVersion = "1.0.4"
  private val AlpakkaS3Version = "2.0.0"
  private val AkkaHttpPlayJsonVersion = "1.27.0"
  private val AmazonS3SdkVersion = "1.11.789"

  val Common: Seq[ModuleID] = Seq(
    "com.lightbend.akka" %% "akka-stream-alpakka-s3" % AlpakkaS3Version,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml" % AkkaHttpVersion,
    "com.amazonaws" % "aws-java-sdk-s3" % AmazonS3SdkVersion
  )

  val package1: Seq[ModuleID] = Seq(
    "io.swagger" %% "swagger-play2" % "1.7.1",
    "org.webjars" % "swagger-ui" % "3.17.1",
    guice,
    "com.h2database" % "h2" % "1.4.199",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
  )

  val package2: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
    "de.heikoseeberger" %% "akka-http-play-json" % AkkaHttpPlayJsonVersion,
    "com.typesafe.akka" %% "akka-stream-kafka-testkit" % KafkaStreamsTestVersion
  )
}