import ReleaseTransformations._
import sbtrelease.ReleaseStateTransformations.setNextVersion

name := "Demo"
organization in ThisBuild := "com.example"
organizationName in ThisBuild := "Example"
scalaVersion := "2.13.2"

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(
    common,
    package1,
    package2
  )

lazy val common = (project in file("common"))
  .settings(
    name := "common",
    commonSettings,
    libraryDependencies ++= Dependencies.Common,
//    dependencyOverrides ++= Seq (
//      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.10.3",
//      "org.scala-lang.modules" % "scala-xml" % "1.1.0",
//      "commons-logging" % "commons-logging" % "1.1.3"
//    )
  )

lazy val package1 = project.in(file("package1"))
  .enablePlugins(PlayScala,BuildInfoPlugin,JavaAppPackaging,DockerPlugin)
  .settings(
    name := "package1",
    commonSettings,
    buildInfoOptions += BuildInfoOption.ToJson,
    DockerSettings.global,
    libraryDependencies ++= Dependencies.Common ++ Dependencies.package1,
    dependencyOverrides ++= Seq (),
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      releaseStepTask(publish in Docker),
      setNextVersion
    ),
  )
  .dependsOn(
    common % "compile->compile;test->test"
  )


lazy val package2 = project.in(file("package2"))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    name := "package2",
    commonSettings,
    DockerSettings.global,
    libraryDependencies ++= Dependencies.Common ++ Dependencies.package2,
    dependencyOverrides ++= Seq (),
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      releaseStepTask(publish in Docker),
      setNextVersion
    ),
  )
  .dependsOn(
    common % "compile->compile;test->test",
    package1 % "compile->compile;test->test"
  )

lazy val commonSettings = Seq(
  Global / onChangedBuildSource := ReloadOnSourceChanges,
  releaseIgnoreUntrackedFiles := true,
  skip in publish := true,
  scalacOptions ++=  Seq(
    "-unchecked",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "utf8"
  ),
  resolvers ++= Seq(
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,                  // : ReleaseStep
  inquireVersions,                            // : ReleaseStep
  runClean,                                   // : ReleaseStep
  runTest,                                    // : ReleaseStep
  setReleaseVersion,                          // : ReleaseStep
  commitReleaseVersion,                       // : ReleaseStep, performs the initial git checks
  tagRelease,                                 // : ReleaseStep
  publishArtifacts,
  releaseStepTask(publish in Docker in package1),
  releaseStepTask(publish in Docker in package2),  // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                             // : ReleaseStep
  commitNextVersion,                          // : ReleaseStep
  pushChanges                                 // : ReleaseStep, also checks that an upstream branch is properly configured
)