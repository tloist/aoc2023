import scala.math.max

object Domain:
  enum Cube:
    case Red, Blue, Green

  opaque type CubeSet = Map[Cube, Int]
  extension (set: CubeSet)
    def power: Int = set.values.product
    def isPossibleWith(available: CubeSet): Boolean = set.forall: (drawnColor, drawnAmount) =>
      drawnAmount <= available.getOrElse(drawnColor, 0)
    def minimalCubesBetween(other: CubeSet): CubeSet = (for {
      cube <- set.keySet ++ other.keySet
      required = max(set.getOrElse(cube, 0), other.getOrElse(cube, 0))
    } yield cube -> required).toMap

  case class Game(id: Int, sets: Seq[CubeSet]):
    def isPossibleWith(availableCubes: CubeSet): Boolean = sets.forall(_ isPossibleWith availableCubes)
    def fewestBagOfCube: CubeSet = sets.foldLeft(cubeSetOf()) { (minYet, next) => minYet minimalCubesBetween next }

  opaque type Games = Seq[Game]
  extension (games: Games)
    def onlyPossibleWith(set: CubeSet): Games = games.filter(_ isPossibleWith set)
    def sumOfIds: Int = games.map(_.id).sum
    def sumOfFewestCubePowers: Int = games.map(_.fewestBagOfCube.power).sum
    def size = games.size

  export Cube._
  def cubeSetOf(cubes: (Cube, Int)*): CubeSet = cubes.toMap.withDefaultValue(0)
  def gamesOf(games: Game*): Games = games