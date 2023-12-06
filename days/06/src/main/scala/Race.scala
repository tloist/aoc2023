import Distance.Distance
import Duration.Duration
import better.files.Resource

case class Race(time: Duration, distanceToWin: Distance):
  def distanceAfterHoldingButtonFor(pressButtonDuration: Duration): Distance =
    Acceleration.afterHoldingButtonFor(pressButtonDuration) raceFor (time - pressButtonDuration)
  def optionsHowLongToHoldTheButtonToWin: Int =
    (0L to time.asMs).map(Duration.inMs).count(millis => distanceAfterHoldingButtonFor(millis) > distanceToWin)

object Race:
  def parseRaces(input: String): Seq[Race] =
    val (line1, line2) = readRaceFileContent(input)
    def numbersOutOf[T](line: String): Seq[Long] = (line split " ").tail.filterNot(_.isBlank).map(_.trim.toLong)
    val durations = numbersOutOf(line1).map(Duration.inMs)
    val distances = numbersOutOf(line2).map(Distance.inMillimeters)
    require(durations.length == distances.length)
    (durations zip distances).map(Race.apply)

  def parseRace(input: String): Race =
    val (line1, line2) = readRaceFileContent(input)
    def numberOutOf[T](line: String): Long = (line split ":")(1).filterNot(_.isWhitespace).mkString.toLong
    Race(Duration.inMs(numberOutOf(line1)), Distance.inMillimeters(numberOutOf(line2)))

  val parseRaceFromFile: String => Race = readFromFile andThen parseRace
  val parseRacesFromFile: String => Seq[Race] = readFromFile andThen parseRaces

  private def readFromFile(filename: String): String =
    Resource.asString(filename).getOrElse(throw new IllegalArgumentException(s"Couldn't read file '$filename'"))
  private def readRaceFileContent(content: String): (String, String) =
    val Array(line1, line2) = content split "\n"
    require(line1.startsWith("Time:"))
    require(line2.startsWith("Distance:"))
    (line1, line2)