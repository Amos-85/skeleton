import com.typesafe.sbt.packager.Keys.{daemonGroup, daemonGroupGid, daemonUser, daemonUserUid, defaultLinuxInstallLocation, maintainer, packageName}
import com.typesafe.sbt.packager.docker.Cmd
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import sbt.Keys.{name, organization, version, organizationName}


object DockerSettings {
  private lazy val dockerRepoHostStaging: String = sys.env.getOrElse("DOCKER_REGISTRY_HOST_STAGING", "dockerRepo-staging.example.com")
  private lazy val dockerRepoDockerRepository: String = "/example-docker-local"
  private lazy val baseImageRepository = "openjdk:11.0.7-jre-slim"
  private lazy val gitCommit = sys.env.getOrElse("GIT_COMMIT", "local")
  private lazy val versionNumber = sys.env.getOrElse("BRANCH_NAME", "local") + "-" + sys.env.getOrElse("BUILD_NUMBER", "local")
  private lazy val gitUrl = sys.env.getOrElse("GIT_URL", "local")

  lazy val global = Seq(
    dockerUpdateLatest := true,
    version in Docker := version.value,
    maintainer in Docker := organization.value,
    defaultLinuxInstallLocation in Docker := s"/${name.value}/app",
    dockerBaseImage := baseImageRepository,
    dockerExposedPorts := Seq(9000),
//    daemonUser in Docker := "user",
//    daemonGroup in Docker := "user",
//    daemonGroupGid := Some("62084"),
//    daemonUserUid := Some("67333"),
//    dockerCommands ++= Seq(Cmd("USER", "example")),
    packageName in Docker := name.value,
    dockerRepository in Docker := Some(dockerRepoHostStaging + dockerRepoDockerRepository),
    dockerUsername in Docker := Some(organizationName.value.toLowerCase()),
    dockerLabels := Map(
      "org.label-schema.vcs-ref" -> gitCommit,
      "org.label-schema.vendor" -> organization.value,
      "org.label-schema.name" -> organizationName.value,
      "org.label-schema.version" -> versionNumber,
      "org.label-schema.vcs-url" -> gitUrl,
      "com.jfrog.dockerRepo.retention.maxCount" -> "5"
    )
  )
}