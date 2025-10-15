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
      "com.typesafe.play" %% "play-json" % "2.10.5",  // JSON Support
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
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
