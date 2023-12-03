import geo.{Map2D, Point2D, Rectangle, Vector2D}

import java.lang.Character.isDigit
import scala.annotation.tailrec

enum Space:
  case Empty
  case Symbol(c: Char)
  case Digit(no: Int)

  def isSymbol: Boolean = this match
    case Symbol(_) => true
    case _ => false

  def isDigit: Boolean = this match
    case Digit(_) => true
    case _ => false

  def asChar: Char = this match
    case Empty => '.'
    case Symbol(c: Char) => c
    case Digit(no: Int) => no.toString.head

object Space:
  def fromChar(char: Char): Space = char match
    case '.' => Empty
    case d if isDigit(d) => Digit(d.toString.toInt)
    case c => Symbol(c)

case class EngineSchematic(map: Map2D[Int, Space]):
  type Position = Point2D[Int]
  type Area = Rectangle[Int]
  def isSymbol(pos: Position): Boolean = map.get(pos).exists(_.isSymbol)
  def isDigit(pos: Position): Boolean = map.get(pos).exists(_.isDigit)
  def allSymbolPositions: Set[Position] = map.findPositions(_._2.isSymbol)
  def allGears: Map[Position, Int] =
    def isGearCandidate(pos: Position, s: Space): Boolean = s == Space.Symbol('*')
    map.findPositions(isGearCandidate).flatMap: gc =>
      val adjacentNumbers = numbers(gc.andAllSurrounding)
      val gearRatio = if adjacentNumbers.size != 2 then None else Some(adjacentNumbers.values.product)
      gearRatio.map(gc -> _)
    .toMap

  def numbers(positions: Set[Position]): Map[Area, Int] =
    @tailrec def maxLeft(pos: Position): Position =
      if isDigit(pos.left) then maxLeft(pos.left) else pos
    def toNumber(start: Position): (Area, Int) =
      @tailrec def collect(current: String, pos: Position): (Area, Int) =
        if !isDigit(pos) then Rectangle(start, pos) -> current.toInt
        else collect(current + map.get(pos).map(_.asChar.toString).getOrElse(""), pos.right)
      collect("", start)
    val validNumberStarts = positions.filter(isDigit).map(maxLeft)
    validNumberStarts.map(toNumber).toMap

  def sumOfNumbersNextToSymbols: Int = numbers(allSymbolPositions.flatMap(_.andAllSurrounding)).values.sum
  def sumOfAllGearRatios: Int = allGears.values.sum

  extension (p: Position)
    def left: Position = p + Vector2D(-1, 0)
    def right: Position = p + Vector2D(1, 0)

object EngineSchematic:
  def fromRessource(filename: String): EngineSchematic =
    EngineSchematic(Map2D.fromResource(filename, Space.fromChar))

val engineSchematic: EngineSchematic = EngineSchematic.fromRessource("inputA.txt")

@main def part1(): Unit =
  println(s"The sum of all numbers next to symbols is: ${engineSchematic.sumOfNumbersNextToSymbols}")

@main def part2(): Unit =
  println(s"The sum of all gear ratios is: ${engineSchematic.sumOfAllGearRatios}")