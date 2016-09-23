organization := "info.hupel"
name := "slf4j-impl-helper"

scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.10.5", "2.11.8", "2.12.0-RC1")

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-encoding", "UTF-8",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfatal-warnings"
)

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.19"

homepage := Some(url("https://github.com/larsrh/slf4j-impl-helper"))

licenses := Seq("Apache 2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

pomExtra := (
  <developers>
    <developer>
      <id>larsrh</id>
      <name>Lars Hupel</name>
      <url>http://lars.hupel.info</url>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:github.com/larsrh/slf4j-impl-helper.git</connection>
    <developerConnection>scm:git:git@github.com:larsrh/slf4j-impl-helper.git</developerConnection>
    <url>https://github.com/larsrh/slf4j-impl-helper</url>
  </scm>
)

credentials += Credentials(
  Option(System.getProperty("build.publish.credentials")) map (new File(_)) getOrElse (Path.userHome / ".ivy2" / ".credentials")
)


// Release stuff

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true)
)
