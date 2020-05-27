import ReleaseTransformations._
//import sbt.Keys._

name := "Demo"
organization in ThisBuild := "com.example"
organizationName in ThisBuild := "Example"
scalaVersion := "2.12.6"

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
    version := "1.0.0-SNAPSHOT",
    commonSettings,
    libraryDependencies ++= Dependencies.Common
  )

lazy val package1 = project.in(file("package1"))
  .settings(
    name := "package1",
    version := "1.0.0-SNAPSHOT",
    commonSettings,
    DockerSettings.global,
    libraryDependencies ++= Dependencies.Common ++ Dependencies.package1
  )
  .dependsOn(
    common % "compile->compile;test->test"
  )
  .enablePlugins(JavaAppPackaging)

lazy val package2 = project.in(file("package2"))
  .settings(
    name := "package2",
    version := "1.0.0-SNAPSHOT",
    commonSettings,
    DockerSettings.global,
    libraryDependencies ++= Dependencies.Common ++ Dependencies.package2
  )
  .dependsOn(
    common % "compile->compile;test->test",
    package1 % "compile->compile;test->test"
  )
  .enablePlugins(JavaAppPackaging)

lazy val commonSettings = Seq(
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
//  commitReleaseVersion,                       // : ReleaseStep, performs the initial git checks
//  tagRelease,                                 // : ReleaseStep
  publishArtifacts,
//  releaseStepTask(publishLocal in Docker),    // : ReleaseStep, checks whether `publishTo` is properly set up
//  setNextVersion,                             // : ReleaseStep
//  commitNextVersion,                          // : ReleaseStep
//  pushChanges                               // : ReleaseStep, also checks that an upstream branch is properly configured
)