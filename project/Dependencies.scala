import sbt._

object Dependencies {

  private val AkkaHttpVersion = "10.1.5"
  private val KafkaStreamsTestVersion = "1.0.4"
  private val AlpakkaS3Version = "1.1.0"
  private val AkkaHttpPlayJsonVersion = "1.22.0"
  private val AmazonS3SdkVersion = "1.11.789"

  val Common: Seq[ModuleID] = Seq(
    "com.lightbend.akka" %% "akka-stream-alpakka-s3" % AlpakkaS3Version,
    "com.amazonaws" % "aws-java-sdk-s3" % AmazonS3SdkVersion
  )

  val package1: Seq[ModuleID] = Seq(
    "io.swagger" %% "swagger-play2" % "1.6.1",
    "org.webjars" % "swagger-ui" % "3.17.1"
  )

  val package2: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
    "de.heikoseeberger" %% "akka-http-play-json" % AkkaHttpPlayJsonVersion,
    "com.typesafe.akka" %% "akka-stream-kafka-testkit" % KafkaStreamsTestVersion
  )
}
