import com.typesafe.sbt.packager.Keys.{daemonGroupGid, daemonUser,daemonGroup, daemonUserUid, defaultLinuxInstallLocation, maintainer, packageName}
import com.typesafe.sbt.packager.docker.{ExecCmd,DockerChmodType,Cmd}
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import sbt.Keys.{name, organization, organizationName, version}


object DockerSettings {
  private lazy val dockerRepoHostStaging: String = sys.env.getOrElse("DOCKER_REGISTRY_HOST_STAGING", "docker.io/")
  private lazy val dockerRepoDockerRepository: String = sys.env.getOrElse("DOCKERHUB_USER","local")
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
    dockerChmodType := DockerChmodType.UserGroupWriteExecute,
    dockerAdditionalPermissions += (DockerChmodType.UserGroupWriteExecute, (defaultLinuxInstallLocation in Docker).value),
//    dockerChmodType := DockerChmodType.Custom("chmod -R u=rwX,g=rwX"),
//    daemonUser in Docker := "app",
//    daemonGroup in Docker := "app",
//    daemonGroupGid := Some("30444"),
//    daemonUserUid := Some("30444"),
    dockerCommands ++= Seq(
      Cmd("RUN", "groupadd", "-g", "30444", "app"),
      Cmd("RUN","useradd", "--system", "-M", "-u", "30444", "-g", "30444", "app"),
      Cmd("RUN","chown", "-R", "app:app",s"${(defaultLinuxInstallLocation in Docker).value}"),
      Cmd("USER", "app")
    ),
    dockerCommands := dockerCommands.value.filterNot {
//      case Cmd("RUN", args @ _*) => args.contains("id")
      case Cmd("USER",args @ _*) => args.contains("1001:0")
      case cmd                       => false
    },
    packageName in Docker := name.value,
    dockerRepository := Some(dockerRepoHostStaging + dockerRepoDockerRepository),
//    dockerUsername in Docker := Some(organizationName.value.toLowerCase()),
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