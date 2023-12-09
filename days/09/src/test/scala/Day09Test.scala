import munit.FunSuite

class Day09Test extends FunSuite:
  val report1: OasisReport = OasisReport.initial(0, 3, 6, 9, 12, 15)
  val report2: OasisReport = OasisReport.initial(1, 3, 6, 10, 15, 21)
  val report3: OasisReport = OasisReport.initial(10, 13, 16, 21, 30, 45)
  val report4: OasisReport = OasisReport.initial(-1, 9, 31, 63)

  testParsingOasisReport("0 3 6 9 12 15", report1)
  testParsingOasisReport("1 3 6 10 15 21", report2)
  testParsingOasisReport("10 13 16 21 30 45", report3)
  testParsingOasisReport("-1 9 31 63", report4)

  test("Example 1 -- Line '0 3 6 9 12 15' has step 1: 3, 3, 3, 3, 3"):
    assertEquals(report1.stepDown, OasisReport(List(3, 3, 3, 3, 3), 1))

  testStepDownEndsAt(report1, 2)
  testStepDownEndsAt(report2, 3)
  testStepDownEndsAt(report3, 4)

  testExtrapolateNext(report1, 18)
  testExtrapolateNext(report2, 28)
  testExtrapolateNext(report3, 68)

  testExtrapolatePrev(report3, 5)

  test("Example 1 -- Sum of all expanded lines is 114"):
    assertEquals(OasisReport.parseFile("example.txt").map(_.extrapolateNext).sum, 114)

  test("Example 1 -- Sum of all prepended lines is 2"):
    assertEquals(OasisReport.parseFile("example.txt").map(_.extrapolatePrev).sum, 2)

  def testParsingOasisReport(input: String, report: OasisReport)(implicit loc: munit.Location): Unit =
    test(s"Example 1 -- Line '$input' can be parsed as Oasis Report"):
      assertEquals(OasisReport.parse(input), report)

  def testStepDownEndsAt(report: OasisReport, lvl: Int)(implicit loc: munit.Location) =
    test(s"Example 1 -- Line '${report.numbers.mkString(" ")}' step down ends at step $lvl"):
      assertEquals(report.stepsDown.last.step, lvl)

  def testExtrapolateNext(report: OasisReport, expectedNumber: Int)(implicit loc: munit.Location) =
    test(s"Example 1 -- Line '${report.numbers.mkString(" ")}' is expanded with $expectedNumber "):
      assertEquals(report.extrapolateNext, expectedNumber)

  def testExtrapolatePrev(report: OasisReport, expectedNumber: Int)(implicit loc: munit.Location) =
    test(s"Example 2 -- Line '${report.numbers.mkString(" ")}' is prepended with $expectedNumber "):
      assertEquals(report.extrapolatePrev, expectedNumber)
