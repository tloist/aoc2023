import sbt.*

object Dependencies {
  val cats = "org.typelevel" %% "cats-core" % "2.10.0"
  val catsParse = "org.typelevel" %% "cats-parse" % "1.0.0"
  val betterFiles = "com.github.pathikrit" % "better-files_3" % "3.9.2"
  val guava = "com.google.guava" % "guava" % "32.1.3"
  val mUnit = "org.scalameta" %% "munit" % "1.0.0-M10"
  val monocleCore = "dev.optics" %% "monocle-core"  % "3.2.0"
  val monocleMacro = "dev.optics" %% "monocle-macro" % "3.2.0"
}
