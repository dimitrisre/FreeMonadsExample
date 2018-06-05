name := "FreeMonadsExample"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.1.0",
  "org.typelevel" %% "cats-free" % "1.0.1",
  "org.typelevel" %% "cats-kernel" % "1.1.0",
  "org.typelevel" %% "cats-macros" % "1.1.0"
)