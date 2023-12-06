import Distance.Distance
import Duration.Duration

object Acceleration:
  opaque type Acceleration = Long

  def inMillimeterPerMs(amount: Int): Acceleration = amount
  def inMillimeterPerMs(amount: Long): Acceleration = amount
  def afterHoldingButtonFor(duration: Duration): Acceleration = inMillimeterPerMs(duration.asMs)

  extension (lhs: Acceleration)
    def raceFor(duration: Duration): Distance = Distance.inMillimeters(lhs * duration.asMs)