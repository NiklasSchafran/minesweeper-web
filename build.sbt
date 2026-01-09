import sbt._
import Keys._
import play.sbt.PlayImport._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "MinesweeperWeb",
    organization := "de.htwg.se",
    version := "1.0.0",
    scalaVersion := "3.3.3",

    libraryDependencies ++= Seq(
      guice,                               // Play Guice DI (optional, aber notwendig für Play)
       "org.apache.pekko" %% "pekko-actor-typed" % "1.1.2",
       "org.apache.pekko" %% "pekko-stream" % "1.1.2",
       "org.apache.pekko" %% "pekko-http" % "1.1.2",
       "com.typesafe.play" %% "play-json" % "2.10.5"
    ),

    dependencyOverrides ++= Seq(
      "com.google.inject" % "guice" % "6.0.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "6.0.0",
      "net.codingwell" %% "scala-guice" % "6.0.0",
      "javax.inject" % "javax.inject" % "1"
    ),

    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked"
    )
  )
