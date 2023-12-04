import better.files.Resource

class Day04Test extends munit.FunSuite {
  val example: String = Resource.asString("example.txt").getOrElse(throw new IllegalStateException(s"Couldn't read input file 'example.txt'"))

  test("Example 1"):
    val points = Card.parseCards(example).map(c => c.no -> c.points).toMap
    assertEquals(points(1), 8)
    assertEquals(points(2), 2)
    assertEquals(points(3), 2)
    assertEquals(points(4), 1)
    assertEquals(points(5), 0)
    assertEquals(points(6), 0)
    assertEquals(points.values.sum, 13)

  test("Example 2"):
    val counts = Card.parseCards(example).play
    assertEquals(counts(1), 1)
    assertEquals(counts(2), 2)
    assertEquals(counts(3), 4)
    assertEquals(counts(4), 8)
    assertEquals(counts(5), 14)
    assertEquals(counts(6), 1)
    assertEquals(counts.values.sum, 30)

}