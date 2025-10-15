error id: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/build.sbt:
file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -dependencyOverrides.
	 -dependencyOverrides#
	 -dependencyOverrides().
	 -scala/Predef.dependencyOverrides.
	 -scala/Predef.dependencyOverrides#
	 -scala/Predef.dependencyOverrides().
offset: 196
uri: file:///C:/Users/Niklas/Studium/Semester8/Web/minesweeper-web/build.sbt
text:
```scala
name := """minesweeper-web"""
organization := "minesweeper-WebApp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

@@dependencyOverrides ++= Seq(
      "com.google.inject" % "guice" % "6.0.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "6.0.0",
      "net.codingwell" %% "scala-guice" % "6.0.0",
      "javax.inject" % "javax.inject" % "1"
    )% Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "minesweeper-WebApp.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "minesweeper-WebApp.binders._"

```


#### Short summary: 

empty definition using pc, found symbol in pc: 