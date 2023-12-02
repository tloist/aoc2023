import scala.util.matching.Regex
import Data._

object Parse {
  def cube(input: String): Option[Cube] = Cube.values.find(_.toString.toLowerCase == input)

  def game(line: String): Game = line match
    case gamePattern(no: String, sets: String) =>
      Game(no.toInt, sets.split(';').map: setLine =>
        setLine.split(',').map:
          case cubePattern(amount: String, color: String) =>
            cube(color).map(_ -> amount.toInt) getOrElse (
              throw new IllegalArgumentException(s"Illegal color for cube specified!\n\t$color"))
          case cubeLine =>
            throw new IllegalArgumentException(s"Line does not represent a valid amount for a cube color!\n\t$cubeLine")
        .toMap
      )
    case _ => throw new IllegalArgumentException(s"Line does not represent a game!\n\t$line")

  def games(input: String): Seq[Game] = input.split('\n').map(game)

  private val gamePattern: Regex = """Game (\d+): (.*)""".r
  private val cubePattern: Regex = """\s*(\d+) (\D+)\s*""".r
}
