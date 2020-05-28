import java.time.LocalDateTime

import sbt.Keys._
import sbtbuildinfo.BuildInfoPlugin.autoImport._

object BuildInfoSettings {

  lazy val settings = Seq(
    buildInfoPackage := "meta",
    buildInfoOptions += BuildInfoOption.ToJson,
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      BuildInfoKey.action("buildTime") {
        LocalDateTime.now().toString
      },
      buildInfoBuildNumber,
      "buildInfoBuildUrl" -> System.getenv().getOrDefault("BUILD_URL", "---"),
      "buildInfoCommitId" -> System.getenv().getOrDefault("GIT_COMMIT", "---")
    ),
    buildInfoBuildNumber := { System.getenv().getOrDefault("BUILD_NUMBER", "0").toInt }
  )
}
