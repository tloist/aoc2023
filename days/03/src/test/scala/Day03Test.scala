class Day03Test extends munit.FunSuite {
  val engineSchematic: EngineSchematic = EngineSchematic.fromRessource("example.txt")

  test("Example 1"):
    assertEquals(engineSchematic.sumOfNumbersNextToSymbols, 4361)

  test("Example 2"):
    assertEquals(engineSchematic.allGears.values.sum, 467835)

}