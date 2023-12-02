class Day01TestB extends munit.FunSuite {
  val example2: String =
    """two1nine
      |eightwothree
      |abcone2threexyz
      |xtwone3four
      |4nineeightseven2
      |zoneight234
      |7pqrstsixteen""".stripMargin

  given TokenMapping = tokensB

  test("Overlapping textual numbers") {
    assertEquals(18, sumOf("oneight", calibrationValue))
  }

  test("Full Example 2") {
    assertEquals(281, sumOf(example2, calibrationValue))
  }

}