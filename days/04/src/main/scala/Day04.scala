import better.files.Resource

import scala.annotation.targetName

case class Card(no: Int, winning: Set[Int], drawn: List[Int]):
  override def toString: String = s"Card $no: ${winning.mkString(s" ")} | ${drawn.mkString(" ")}"
  def countWinningNumbers: Int = drawn.count(winning.contains)
  def points: Int =
    val won = countWinningNumbers
    if won == 0 then 0 else Math.pow(2, won-1).toInt
object Card:
  private val linePattern = """Card\s*(\d+): ([\s\d]*) \| ([\s\d]*)""".r

  def parseFromLine(line: String): Card = line match
    case linePattern(no: String, winning: String, drawn: String) =>
      val winNo = winning.split(" ").filterNot(_.trim.isBlank).map(_.trim.toInt).toSet
      val drawnNo = drawn.split(" ").filterNot(_.trim.isBlank).map(_.trim.toInt).toList
      Card(no.toInt, winNo, drawnNo)
    case _ => throw new IllegalArgumentException(s"Line '$line' does not represent a card")

  def parseCards(input: String): Seq[Card] = input.split('\n').map(parseFromLine)



val cards: Seq[Card] = Card.parseCards(Resource.asString("input.txt")
  .getOrElse(throw new IllegalStateException(s"Couldn't read input file 'input.txt'")))

type CardCounts = Map[Int, Int]
extension (counts: CardCounts)
  @targetName("add")
  def +(other: CardCounts): CardCounts = (for { // Could use Cats Semigroup as well…
    key <- counts.keySet ++ other.keySet
    value = counts.getOrElse(key, 0) + other.getOrElse(key, 0)
  } yield key -> value).toMap

extension (cards: Seq[Card])
  def play: Map[Int, Int] =
    val initialCardPile = cards.map(c => c.no -> 1).toMap
    cards.foldLeft(initialCardPile): (cardPile, currentCard) =>
      val currentCount = cardPile.getOrElse(currentCard.no, 0)
      val additionalCount = (currentCard.no + 1 to (currentCard.no + currentCard.countWinningNumbers)).map(_ -> currentCount).toMap
      cardPile + additionalCount


@main def part1(): Unit =
  println(s"Cards are worth in total ${cards.map(_.points).sum}")

@main def part2(): Unit =
  println(s"The total number of scratchcards that you own is ${cards.play.values.sum}")
