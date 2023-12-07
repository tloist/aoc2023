import Card.PredicateGen
import Combination.{FiveOfAKind, FourOfAKind, FullHouse, HighCard, OnePair, ThreeOfAKind, TwoPair}
import better.files.Resource

enum Mode:
  case Part1
  case Part2

enum Card(val char: Char):
  case Ace extends Card('A')
  case King extends Card('K')
  case Queen extends Card('Q')
  case Jack extends Card('J')
  case Ten extends Card('T')
  case Nine extends Card('9')
  case Eight extends Card('8')
  case Seven extends Card('7')
  case Six extends Card('6')
  case Five extends Card('5')
  case Four extends Card('4')
  case Three extends Card('3')
  case Two extends Card('2')
  def asChar: Char = char

object Card:
  trait PredicateGen:
    def is(c: Card): Card => Boolean

  def fromChar(c: Char): Option[Card] = Card.values.find(_.char == c)

  given (using mode: Mode): Ordering[Card] = mode match
    case Mode.Part1 => Ordering.by(_.ordinal)
    case Mode.Part2 => Ordering.by(List(Ace, King, Queen, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two, Jack).indexOf)

  given (using mode: Mode): PredicateGen = mode match
    case Mode.Part1 => c => test => c == test
    case Mode.Part2 => c => test => c == test || test == Jack



case class Hand(cards: Seq[Card]):
  def without(card: Card)(using it: Card.PredicateGen): Hand = Hand(cards.filterNot(it is card))
  def counted(using theCard: Card.PredicateGen): Map[Card, Int] =
    Card.values.map(c1 => c1 -> cards.count(theCard is c1)).toMap
  def hasDuplicateOf(count: Int)(using matcher: Card.PredicateGen): Boolean = duplicateOf(count).isDefined
  def duplicateOf(count: Int)(using matcher: Card.PredicateGen): Option[Card] = counted.find(_._2 >= count).map(_._1)
  def hasTwoDuplicatesOf(countA: Int, countB: Int)(using matcher: Card.PredicateGen): Boolean =
    duplicateOf(countA).exists: cardA =>
      without(cardA).hasDuplicateOf(countB)
  override def toString: String = cards.map(_.char).mkString
  def tupled: (Card, Card, Card, Card, Card) = (cards(0), cards(1), cards(2), cards(3), cards(4))

object Hand:
  def parse(input: String): Hand =
    val cards = for { char <- input; card <- Card.fromChar(char) } yield card
    require(cards.length == 5, s"Couldn't parse input '$input' as a hand of cards!")
    Hand(cards)
  given (using Mode): Ordering[Hand] = Ordering.by(hand => Combination.of(hand) *: hand.tupled)


enum Combination:
  case FiveOfAKind
  case FourOfAKind
  case FullHouse
  case ThreeOfAKind
  case TwoPair
  case OnePair
  case HighCard

  def isFullfilled(hand: Hand)(using matcher: PredicateGen): Boolean = this match
    case FiveOfAKind => hand hasDuplicateOf 5
    case FourOfAKind => hand hasDuplicateOf 4
    case FullHouse => hand hasTwoDuplicatesOf(3, 2)
    case ThreeOfAKind => hand hasDuplicateOf 3
    case TwoPair => hand hasTwoDuplicatesOf(2, 2)
    case OnePair => hand hasDuplicateOf 2
    case HighCard => true


object Combination:
  def of(hand: Hand)(using matcher: PredicateGen): Combination = values.find(_.isFullfilled(hand)).getOrElse(
    throw new IllegalStateException(s"You've found a hand that not even fulfills the requirements of a high card!"))
  given Ordering[Combination] = Ordering.by(_.ordinal)

case class Bid(hand: Hand, bid: Long)
object Bid:
  def parse(input: String): Bid =
    val Array(handPart, bidPart) = input split " "
    Bid(Hand parse handPart, bidPart.toInt)
  def parseBids(input: String): Seq[Bid] =
    (input split "\n") map parse
  def parseFile(filename: String): Seq[Bid] =
    parseBids(Resource.asString(filename).getOrElse(throw new IllegalArgumentException(s"Couldn't read file '$filename'")))
  given (using Ordering[Hand]): Ordering[Bid] = Ordering.by(_.hand)

extension [T: Ordering] (things: Seq[T])
  def ranked: Map[T, Int] = things.sorted.zipWithIndex.map { (thing, index) => thing -> (things.size - index) }.toMap

extension (bids: Seq[Bid])
  def totalWinnings(using mode: Mode): Long = bids.ranked.map((bid, rank) => rank * bid.bid).sum

val bids: Seq[Bid] = Bid.parseFile("input.txt")

@main def part1(): Unit =
  given Mode = Mode.Part1
  println(s"Total winnings are: ${bids.totalWinnings}")

@main def part2(): Unit =
  given Mode = Mode.Part2
  println(s"Total winnings are: ${bids.totalWinnings}")
