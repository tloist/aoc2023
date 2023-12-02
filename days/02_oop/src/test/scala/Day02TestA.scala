import Domain._

class Day02TestA extends munit.FunSuite {
  val example1: String =
    """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      |Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      |Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      |Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".stripMargin

  test("Example 1 -- Parse Line 1"):
    val game = Parse.game(example1.split('\n').head)
    assertEquals(game.id, 1)
    assertEquals(game.sets.size, 3)
    assertEquals(game.sets(0), cubeSetOf(Blue -> 3, Red -> 4))
    assertEquals(game.sets(1), cubeSetOf(Red -> 1, Green -> 2, Blue -> 6))
    assertEquals(game.sets(2), cubeSetOf(Green -> 2))

  test("Example 1 -- Parse all games"):
    val games = Parse.seqOfGames(example1)
    assertEquals(games.size, 5)
    assertEquals(games.last.id, 5)

  val bagOfCubes: CubeSet = cubeSetOf(Red -> 12, Green -> 13, Blue -> 14)

  test("Example 1 -- All possible games are detected possible"):
    val games = Parse.seqOfGames(example1)
    val possibleGameNo = Set(1, 2, 5)
    games.filter(g => possibleGameNo(g.id)).foreach: game =>
      assert(game.isPossibleWith(bagOfCubes))

  test("Example 1 -- All impossible games are detected impossible"):
    val games = Parse.seqOfGames(example1)
    val impossible = Set(3, 4)
    games.filter(g => impossible(g.id)).foreach: game =>
      assert(!game.isPossibleWith(bagOfCubes))

}