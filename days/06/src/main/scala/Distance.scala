object Distance:
  opaque type Distance = Long
  // Providing the ordered type class to our 'new' types, which means we can compare them with other Distances
  given(using num: Ordering[Long]): Ordering[Distance] = num

  def inMillimeters(amount: Long): Distance = amount

  extension (distance: Distance)
    def toString: String = s"$distance millimeters"
