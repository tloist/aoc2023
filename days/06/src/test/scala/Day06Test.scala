class Day06Test extends munit.FunSuite:
  val race1: Race = Race(7.ms, 9.millimeters)
  val race2: Race = Race(15.ms, 40.millimeters)
  val race3: Race = Race(30.ms, 200.millimeters)

  test("Example 1 - Race 1 - Hold button for 0 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 0.ms, 0.millimeters)

  test("Example 1 - Race 1  - Hold button for 1 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 1.ms, 6.millimeters)

  test("Example 1 - Race 1  - Hold button for 2 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 2.ms, 10.millimeters)

  test("Example 1 - Race 1  - Hold button for 3 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 3.ms, 12.millimeters)

  test("Example 1 - Race 1  - Hold button for 4 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 4.ms, 12.millimeters)

  test("Example 1 - Race 1  - Hold button for 5 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 5.ms, 10.millimeters)

  test("Example 1 - Race 1  - Hold button for 6 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 6.ms, 6.millimeters)

  test("Example 1 - Race 1  - Hold button for 7 milliseconds then race"):
    assertEquals(race1 distanceAfterHoldingButtonFor 7.ms, 0.millimeters)

  test("Example 1 - Race 1  - Options how long the button can be pressed to win"):
    assertEquals(race1.optionsHowLongToHoldTheButtonToWin, 4)

  test("Example 1 - Race 2  - Options how long the button can be pressed to win"):
    assertEquals(race2.optionsHowLongToHoldTheButtonToWin, 8)

  test("Example 1 - Race 3  - Options how long the button can be pressed to win"):
    assertEquals(race3.optionsHowLongToHoldTheButtonToWin, 9)

  test("Example 1 - Product of all races"):
    assertEquals(Seq(race1, race2, race3).map(_.optionsHowLongToHoldTheButtonToWin).product, 288)

  test("Example 1 - Parse races from file"):
    val races = Race.parseRacesFromFile("example.txt")
    assertEquals(races.length, 3)
    assertEquals(races(0).time, 7.ms)
    assertEquals(races(0).distanceToWin, 9.millimeters)

    assertEquals(races(1).time, 15.ms)
    assertEquals(races(1).distanceToWin, 40.millimeters)

    assertEquals(races(2).time, 30.ms)
    assertEquals(races(2).distanceToWin, 200.millimeters)

  test("Example 2 - Parse race from file"):
    assertEquals(Race.parseRaceFromFile("example.txt"), Race(71530.ms, 940200.millimeters))

  val greatRace: Race = Race(71530.ms, 940200.millimeters)
  test("Example 2 - Number of options how to beat the big race"):
    assertEquals(greatRace.optionsHowLongToHoldTheButtonToWin, 71503)