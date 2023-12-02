object Data {
  enum Cube:
    case Red, Blue, Green

  type CubeCount = Map[Cube, Int]
  case class Game(id: Int, sets: Seq[CubeCount])

  export Cube.*
}
