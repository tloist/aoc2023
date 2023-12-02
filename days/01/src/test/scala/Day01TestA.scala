class Day01TestA extends munit.FunSuite {
  val example1: String =
    """1abc2
      |pqr3stu8vwx
      |a1b2c3d4e5f
      |treb7uchet""".stripMargin
  given TokenMapping = tokensA

  test("Example 1") {
    assertEquals(sumOf(example1, calibrationValue), 142)
  }
}