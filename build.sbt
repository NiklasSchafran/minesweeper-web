import sbt._
import Keys._
import play.sbt.PlayImport._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "MinesweeperWeb",
    organization := "de.htwg.se",
    version := "1.0.0",

    scalaVersion := "2.13.16",

    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-json" % "2.9.4",
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    ),

    dependencyOverrides ++= Seq(
      "com.google.inject" % "guice" % "5.1.0",
      "javax.inject" % "javax.inject" % "1"
    ),

    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked"
    )
  )
