import scala.annotation.targetName

object Duration:
  opaque type Duration = Long

  // Providing the ordered type class to our 'new' types, which means we can compare them with other Distances
  given(using num: Ordering[Long]): Ordering[Duration] = num

  def inMs(amount: Int): Duration = amount
  def inMs(amount: Long): Duration = amount

  extension (lhs: Duration)
    def toString: String = s"$lhs ms"
    @targetName("add") def +(rhs: Duration): Duration = lhs + rhs
    @targetName("minus") def -(rhs: Duration): Duration = lhs - rhs

    def asMs: Long = lhs
