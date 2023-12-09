case class OasisReport(numbers: List[Int], step: Int):
  def stepDown: OasisReport = OasisReport(numbers.sliding(2).map(_.reduce((a,b) => b-a)).toList, step + 1)
  def isAllZero: Boolean = numbers.forall(_ == 0)
  def stepsDown: LazyList[OasisReport] = this #:: LazyList.unfold(this): prev =>
    if prev.isAllZero then None
    else
      val next = prev.stepDown
      Option((next, next))
  def extrapolateNext: Int = stepsDown.foldRight(0) { (report, previousNumber) => report.last + previousNumber }
  def extrapolatePrev: Int = stepsDown.foldRight(0) { (report, previousNumber) => report.head - previousNumber }
  export numbers.{head, last}

object OasisReport:
  def initial(numbers: Int*): OasisReport = OasisReport(numbers.toList, 0)
  def parse(line: String): OasisReport =
    OasisReport((line split " ").map(_.trim.toInt).toList, 0)
  def parseFileContent(input: String): Seq[OasisReport] = (input split "\n").map(parse)
  def parseFile(filename: String): Seq[OasisReport] = (AoC.fileContent andThen parseFileContent)(filename)

val reports: Seq[OasisReport] = OasisReport.parseFile("input.txt")

@main def part1(): Unit = println(s"The sum of all extrapolated next values is: ${reports.map(_.extrapolateNext).sum}")
@main def part2(): Unit = println(s"The sum of all extrapolated previous values is: ${reports.map(_.extrapolatePrev).sum}")