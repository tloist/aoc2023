import better.files.Resource

object AoC:
  def fileContent(filename: String) = Resource.asString(filename)
    .getOrElse(throw new IllegalStateException(s"Couldn't read input file '$filename'"))

