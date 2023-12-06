import scala.annotation.targetName

// Is there no predefined extension methods in the standard library for this?!
// Scala 2 hat OrderingOps, but what about Scala 3?
extension [T: Ordering] (lhs: T)
  @targetName("lessThan") def <(rhs: T): Boolean = Ordering[T].lt(lhs, rhs)
  @targetName("lessThanOrEqual") def <=(rhs: T): Boolean = Ordering[T].lteq(lhs, rhs)
  @targetName("greater") def >(rhs: T): Boolean = Ordering[T].gt(lhs, rhs)
  @targetName("greaterThanOrEqual") def >=(rhs: T): Boolean = Ordering[T].gteq(lhs, rhs)
  @targetName("equivalent") def equiv(rhs: T): Boolean = Ordering[T].equiv(lhs, rhs)
  @targetName("maximum") def max(rhs: T): T = Ordering[T].max(lhs, rhs)
  @targetName("minimum") def min(rhs: T): T = Ordering[T].min(lhs, rhs)