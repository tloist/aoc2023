import better.files.Resource

type TokenMapping = Map[String, Int]

case class State(first: Int, last: Int) {
  def asResult: Int = first * 10 + last
}

/* Disclaimer: This is something that could have been done easily with a parser combinator library as
 * like Cats-Parse: https://typelevel.org/cats-parse/ which I chose not to use here, because it feels a bit odd for
 * day 1 to use this kind of tooling.
 */
val tokensA: TokenMapping = (0 to 9).map(i => i.toString -> i).toMap
val additionTokensB: TokenMapping = Map(
  "zero" -> 0,
  "one" -> 1,
  "two" -> 2,
  "three" -> 3,
  "four" -> 4,
  "five" -> 5,
  "six" -> 6,
  "seven" -> 7,
  "eight" -> 8,
  "nine" -> 9
)
val tokensB: TokenMapping = tokensA ++ additionTokensB

def calibrationValue(line: String)(using mapping: TokenMapping): Option[Int] =
  def rec(current: Option[State], remaining: String): Option[State] =
    if remaining.isEmpty then current
    else
      val matchingToken = mapping.keys.find(remaining.startsWith)
      matchingToken.map { token =>
        val value = mapping(token)
        val newState = current.map(old => State(old.first, value)).getOrElse(State(value, value))
        // We could skip more here if we would statically calculate the maximal overlap between 2 mappings
        // skipping a single character is 'safe' but slow
        rec(Some(newState), remaining.substring(1))
      } getOrElse rec(current, remaining.substring(1))
  rec(None, line).map(_.asResult)

def sumOf(input: String, lineFunc: String => Option[Int])(using tokenMapping: TokenMapping): Int =
  input.split('\n').flatMap(calibrationValue).sum

val input: String = Resource.asString("inputA.txt").getOrElse(throw new IllegalStateException("Couldn't read input file"))

@main def part1(): Unit =
  given TokenMapping = tokensA
  val sum = sumOf(input, calibrationValue)
  println(s"Calibration value is $sum")

@main def part2(): Unit =
  given TokenMapping = tokensB
  val sum = sumOf(input, calibrationValue)
  println(s"Calibration value is $sum")

