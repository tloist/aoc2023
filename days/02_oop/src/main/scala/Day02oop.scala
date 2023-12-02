import Domain._

val games: Games = Parse.gamesFromFile("input.txt")
val availableCubes: CubeSet = cubeSetOf(Red -> 12, Green -> 13, Blue -> 14)

@main def part1(): Unit =
  println(s"The sum of the IDs of all possible games is ${games.onlyPossibleWith(availableCubes).sumOfIds}")

@main def part2(): Unit =
  println(s"The sum of the power for all minimal required cubes is ${games.sumOfFewestCubePowers}")
