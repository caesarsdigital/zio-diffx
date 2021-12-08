package zio.diffx

// import munit.Assertions._
// import munit.Location
import zio._
import zio.console._
import zio.test.Assertion._
import zio.test._
import zio.test.environment._
import zio.test.Assertion.Render._

import com.softwaremill.diffx.{ConsoleColorConfig, Diff}

trait DiffxAssertions {

  def matchesTo[A: Diff](expected: A): Assertion[A] =
    Assertion.assertionDirect("matchesTo")(param(expected)) { actual =>
      val result = Diff.compare(expected, actual)
      isTrue.label(result.show())(result.isIdentical)
    }
}
