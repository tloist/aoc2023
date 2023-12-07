import Combination._

class Day07TestA extends munit.FunSuite:
  given Mode = Mode.Part1

  test("Example 1 - Cards are sorted correctly"):
    assertEquals(Card.values.sorted.map(_.char).mkString, "AKQJT98765432")

  testHandIsDetectedAsA("AAAAA", FiveOfAKind)
  testHandIsDetectedAsA("AA8AA", FourOfAKind)
  testHandIsDetectedAsA("23332", FullHouse)
  testHandIsDetectedAsA("TTT98", ThreeOfAKind)
  testHandIsDetectedAsA("23432", TwoPair)
  testHandIsDetectedAsA("A23A4", OnePair)
  testHandIsDetectedAsA("23456", HighCard)
  testHandIsDetectedAsA("32T3K", OnePair)
  testHandIsDetectedAsA("T55J5", ThreeOfAKind)
  testHandIsDetectedAsA("KK677", TwoPair)
  testHandIsDetectedAsA("KTJJT", TwoPair)
  testHandIsDetectedAsA("QQQJA", ThreeOfAKind)

  val _32t3k: Hand = Hand.parse("32T3K")
  val t55j5: Hand = Hand.parse("T55J5")
  val kk677: Hand = Hand.parse("KK677")
  val ktjjt: Hand = Hand.parse("KTJJT")
  val qqqja: Hand = Hand.parse("QQQJA")
  val hands: Seq[Hand] = Seq(_32t3k, t55j5, kk677, ktjjt, qqqja)

  test("Example 1 - Hands are ranked correctly"):
    assertEquals(hands.ranked.apply(_32t3k), 1)
    assertEquals(hands.ranked.apply(ktjjt), 2)
    assertEquals(hands.ranked.apply(kk677), 3)
    assertEquals(hands.ranked.apply(t55j5), 4)
    assertEquals(hands.ranked.apply(qqqja), 5)

  test("Example 1 - Hands are sorted correctly"):
    assertEquals(hands.sorted.mkString(","), "QQQJA,T55J5,KK677,KTJJT,32T3K")

  val examples: Seq[Bid] = Bid.parseFile("example.txt")

  test("Example 1 - Total Winnings"):
    assertEquals(examples.totalWinnings, 6440L)

  def testHandIsDetectedAsA[T](handInput: String, combination: Combination)(implicit loc: munit.Location): Unit =
    test(s"Example 1 - '$handInput' is a $combination"):
      val hand = Hand parse handInput
      assertEquals(Combination of hand, combination)
