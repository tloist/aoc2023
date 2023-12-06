import Distance.Distance
import Duration.Duration

extension (lhs: Int)
  def ms: Duration = Duration inMs lhs
  def millimeters: Distance = Distance inMillimeters lhs

@main def part1(): Unit =
  val races = Race.parseRacesFromFile("input.txt")
  val result = races.map(_.optionsHowLongToHoldTheButtonToWin).product
  println(s"Product of the number of ways to beat the record: $result")

@main def part2(): Unit =
  val result = Race.parseRaceFromFile("input.txt").optionsHowLongToHoldTheButtonToWin
  println(s"Product of the number of ways to beat the record: $result")