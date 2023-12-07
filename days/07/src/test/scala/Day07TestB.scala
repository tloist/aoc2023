import Combination.*
import Card.*
import scala.math.Ordered.orderingToOrdered

class Day07TestB extends munit.FunSuite:
  given Mode = Mode.Part2

  test("Example 2 - Cards are sorted correctly"):
    assertEquals(Card.values.sorted.map(_.char).mkString, "AKQT98765432J")

  test("Example 2 - Cards are counted correctly"):
    val hand = Hand parse "T55J5"
    assertEquals(hand counted Ten, 2)   // 1xT + 1xJ
    assertEquals(hand counted Five, 4)  // 3x5 + 1xJ
    assertEquals(hand counted Jack, 1)  //       1xJ
    assertEquals(hand counted Two, 1)   //       1xJ

  testHandIsDetectedAsA("AAAAA", FiveOfAKind)
  testHandIsDetectedAsA("AA8AA", FourOfAKind)
  testHandIsDetectedAsA("23332", FullHouse)
  testHandIsDetectedAsA("TTT98", ThreeOfAKind)
  testHandIsDetectedAsA("23432", TwoPair)
  testHandIsDetectedAsA("A23A4", OnePair)
  testHandIsDetectedAsA("23456", HighCard)
  testHandIsDetectedAsA("32T3K", OnePair)
  testHandIsDetectedAsA("T55J5", FourOfAKind)
  testHandIsDetectedAsA("KK677", TwoPair)
  testHandIsDetectedAsA("KTJJT", FourOfAKind)
  testHandIsDetectedAsA("QQQJA", FourOfAKind)
  testHandIsDetectedAsA("Q2Q2Q", FullHouse)
  testHandIsDetectedAsA("Q2KJJ", ThreeOfAKind)

  val _32t3k: Hand = Hand.parse("32T3K")
  val t55j5: Hand = Hand.parse("T55J5")
  val kk677: Hand = Hand.parse("KK677")
  val ktjjt: Hand = Hand.parse("KTJJT")
  val qqqja: Hand = Hand.parse("QQQJA")
  val hands: Seq[Hand] = Seq(_32t3k, t55j5, kk677, ktjjt, qqqja)

  test("Example 2 - QQQQ2 is higher than JKKK2"):
    val Array(jkkk2, qqqq2) = Array("QQQQ2", "JKKK2").map(Hand.parse)
    assert(qqqq2 > jkkk2)

  test("Example 2 - Hands are sorted correctly"):
    assertEquals(hands.sorted.mkString(","), "KTJJT,QQQJA,T55J5,KK677,32T3K")

  test("Example 2 - Hands are ranked correctly"):
    val ranks = hands.ranked
    assertEquals(ranks(_32t3k), 1)
    assertEquals(ranks(kk677), 2)
    assertEquals(ranks(t55j5), 3)
    assertEquals(ranks(qqqja), 4)
    assertEquals(ranks(ktjjt), 5)

  val examples: Seq[Bid] = Bid.parseFile("example.txt")

  test("Example 2 - Total Winnings"):
    assertEquals(examples.totalWinnings, 5905L)

  test("Example 2 - Reddit Example"):
    val bids = Bid.parseFile("exampleReddit.txt").sorted
    def isSorted[T: Ordering](things: Iterable[T]): Boolean = bids.sliding(2).forall(t => t.head < t(1))
    val values = bids.map(_.bid)
    assert(isSorted(values), s"bids are not sorted: ${values.mkString(", ")}")

  test("Example 2 - Reddit Example shortened"):
    val _2aaaa = Bid.parse("2AAAA 23")
    val q2kjj = Bid.parse("Q2KJJ 13")
    val q2q2q = Bid.parse("Q2Q2Q 19")

    assertEquals(Combination of _2aaaa.hand, FourOfAKind)
    assertEquals(Combination of q2kjj.hand, ThreeOfAKind)
    assertEquals(Combination of q2q2q.hand, FullHouse)

  def testHandIsDetectedAsA[T](handInput: String, combination: Combination)(implicit loc: munit.Location): Unit =
    test(s"Example 2 - '$handInput' is a $combination"):
      val hand = Hand parse handInput
      assertEquals(Combination of hand, combination)

