import sbt._

name := "evaluator"

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies ++= Seq("org.apache.lucene" % "lucene-core" % "6.2.1",
                            "org.apache.lucene" % "lucene-analyzers-common" % "6.2.1",
                            "org.apache.lucene" % "lucene-memory" % "6.2.1",
                            "org.apache.lucene" % "lucene-queryparser" % "6.2.1")
libraryDependencies += "io.spray" % "spray-json_2.11" % "1.3.3"

mainClass in (Compile, run) := Some("com.raptorintelligence.Evaluator")

lazy val util = (project in file("."))
  .enablePlugins(JavaAppPackaging)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

