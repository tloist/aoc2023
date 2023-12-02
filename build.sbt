import Dependencies.*
val scala3Version = "3.3.1"
scalacOptions ++= (
  Seq(
    "-encoding", "UTF-8",
    "-source", "future"
  )
)

name := "Advent of Code 2023"

lazy val day01 = dayProject( 1, "Trebuchet?!")
lazy val day02 = dayProject( 2, "Cube Conundrum")




lazy val common = project
  .in(file("days/common"))
  .settings(
    name := f"Advent-of-Code 2023: Commons",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      betterFiles,
      catsParse,
      mUnit % "test"
    )
  )

def dayProject(day: Int, title: String = ""): Project = Project.apply(f"day_$day%02d", file(f"days/$day%02d"))
  .settings(
    name := f"AoC Day $day%2d" + (if (title.nonEmpty) s" - $title" else ""),
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      cats,
      mUnit % "test"
    )
  )
  .dependsOn(common % "compile->compile;test->test")

def dayProject(day: Int, title: String, additionalDependencies: Seq[ModuleID]): Project  =
  dayProject(day, title).settings(
    libraryDependencies ++= additionalDependencies
  )
