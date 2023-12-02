import Domain._

class Day02TestB extends munit.FunSuite {
  val games: Seq[Game] = Parse.seqOfGames(
    """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      |Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      |Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      |Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".stripMargin)

  test("Game 1 -- Minimal bag"):
    val fewest = games(0).fewestBagOfCube
    assertEquals(fewest, cubeSetOf(Red -> 4, Green -> 2, Blue -> 6))
    assertEquals(fewest.power, 48)

  test("Game 2 -- Minimal bag"):
    val fewest = games(1).fewestBagOfCube
    assertEquals(fewest, cubeSetOf(Red -> 1, Green -> 3, Blue -> 4))
    assertEquals(fewest.power, 12)

  test("Game 3 -- Minimal bag"):
    val fewest = games(2).fewestBagOfCube
    assertEquals(fewest, cubeSetOf(Red -> 20, Green -> 13, Blue -> 6))
    assertEquals(fewest.power, 1560)

  test("Game 4 -- Minimal bag"):
    val fewest = games(3).fewestBagOfCube
    assertEquals(fewest, cubeSetOf(Red -> 14, Green -> 3, Blue -> 15))
    assertEquals(fewest.power, 630)

  test("Game 5 -- Minimal bag"):
    val fewest = games(4).fewestBagOfCube
    assertEquals(fewest, cubeSetOf(Red -> 6, Green -> 3, Blue -> 2))
    assertEquals(fewest.power, 36)


}