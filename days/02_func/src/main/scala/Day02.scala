import better.files.Resource
import cats.Monoid
import cats.syntax.all._
import scala.math.max
import Data._

val readGames: String => Seq[Game] = filename => Parse.games(Resource.asString(filename) getOrElse (
    throw new IllegalStateException(s"Couldn't read input file '$filename'")))
def availableCubes: CubeCount = Map(Red -> 12, Green -> 13, Blue -> 14).withDefaultValue(0)

val isPossible: Game => Boolean = _.sets.forall: set =>
  set.forall: (drawnColor, drawnAmount) =>
    drawnAmount <= availableCubes.getOrElse(drawnColor, 0)
val determineResultA: Seq[Game] => Int = _.filter(isPossible).map(_.id).combineAll
val solveA: String => Int = readGames andThen determineResultA

@main def part1(): Unit =
  val result = solveA("input.txt")
  println(s"The sum of the IDs of all possible games is $result")

given Monoid[CubeCount] with
  def empty: CubeCount = Map.empty.withDefaultValue(0)
  def combine(s: CubeCount, t: CubeCount): CubeCount = (for {
    cube <- s.keySet ++ t.keySet
    maxAmount = max(s.getOrElse(cube, 0), t.getOrElse(cube, 0))
  } yield cube -> maxAmount).toMap

val power: CubeCount => Int = set => set.values.product
val determineResultB: Seq[Game] => Int = games => games
  .map(_.sets.combineAll)
  .map(power)
  .sum
val solveB: String => Int = readGames andThen determineResultB

@main def part2(): Unit =
  val result = solveB("input.txt")
  println(s"The sum of the power for all minimal required cubes is $result")