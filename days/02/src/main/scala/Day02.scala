import better.files.Resource
import scala.util.matching.Regex
import Cube._
import scala.math.max

type BagOfCubes = Map[Cube, Int]
extension (bag: BagOfCubes)
  def power: Int = bag.values.product

enum Cube:
  case Red
  case Blue
  case Green
object Cube:
  def parse(input: String): Option[Cube] = Cube.values.find(_.toString.toLowerCase == input)

case class CubeSet(drawn: BagOfCubes):
  def isPossibleWith(available: BagOfCubes): Boolean = drawn.forall: (drawnColor, drawnAmount) =>
    drawnAmount <= available.getOrElse(drawnColor, 0)

case class Game(id: Int, sets: Seq[CubeSet]):
  def isPossibleWith(availableCubes: BagOfCubes): Boolean = sets.forall(_ isPossibleWith availableCubes)
  def fewestBagOfCube: BagOfCubes = sets.foldLeft(Map.empty[Cube, Int]): (initial, cubeSet) =>
    cubeSet.drawn.foldLeft(initial): (minimal, drawn) =>
      val (cube, amount) = drawn
      minimal + (cube -> max(amount, minimal.getOrElse(cube, 0)))

val gamePattern: Regex = """Game (\d+): (.*)""".r
val cubePattern: Regex = """\s*(\d+) (\D+)\s*""".r

def parseGame(line: String): Game = line match
  case gamePattern(no: String, sets: String) =>
    Game(no.toInt, sets.split(';').map: setLine =>
      CubeSet(setLine.split(',').map:
        case cubePattern(amount: String, color: String) =>
          Cube.parse(color).map(_ -> amount.toInt) getOrElse (
            throw new IllegalArgumentException(s"Illegal color for cube specified!\n\t$color"))
        case cubeLine =>
          throw new IllegalArgumentException(s"Line does not represent a valid amount for a cube color!\n\t$cubeLine")
      .toMap)
    )
  case _ => throw new IllegalArgumentException(s"Line does not represent a game!\n\t$line")

def parseGames(input: String): Seq[Game] = input.split('\n').map(parseGame)

val input: String = Resource.asString("inputA.txt").getOrElse(throw new IllegalStateException("Couldn't read input file"))
val games: Seq[Game] = parseGames(input)
val availableCubes: BagOfCubes = Map(Red -> 12, Green -> 13, Blue -> 14)

@main def part1(): Unit =
  val possibleGames = games.filter(_ isPossibleWith availableCubes)
  val result = possibleGames.map(_.id).sum
  println(s"The sum of the IDs of all possible games is $result")

@main def part2(): Unit =
  val minimalCubes = games.map(_.fewestBagOfCube)
  val result = minimalCubes.map(_.power).sum
  println(s"The sum of the power for all minimal required cubes is $result")