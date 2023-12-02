import better.files.Resource
import Domain._

import scala.util.matching.Regex

object Parse {
  def cube(input: String): Option[Cube] = Cube.values.find(_.toString.toLowerCase == input)

  def game(line: String): Game = line match
    case gamePattern(no: String, sets: String) =>
      Game(no.toInt, sets.split(';').map: setLine =>
        val foo = setLine.split(',').map:
          case cubePattern(amount: String, color: String) =>
            cube(color).map(_ -> amount.toInt) getOrElse (
              throw new IllegalArgumentException(s"Illegal color for cube specified!\n\t$color"))
          case cubeLine =>
            throw new IllegalArgumentException(s"Line does not represent a valid amount for a cube color!\n\t$cubeLine")
        cubeSetOf(foo*)
      )
    case _ => throw new IllegalArgumentException(s"Line does not represent a game!\n\t$line")

  def seqOfGames(input: String): Seq[Game] = input.split('\n').map(game)
  def games(input: String): Games = gamesOf(seqOfGames(input)*)

  def gamesFromFile(filename: String): Games =
    val text = Resource.asString(filename).getOrElse(throw new IllegalStateException(s"Couldn't read input file '$filename'"))
    games(text)

  private val gamePattern: Regex = """Game (\d+): (.*)""".r
  private val cubePattern: Regex = """\s*(\d+) (\D+)\s*""".r
}
