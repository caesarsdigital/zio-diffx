package zio.diffx

import com.softwaremill.diffx.{ConsoleColorConfig, Diff}
import zio.test.Assertion.Render.*
import zio.test.*

trait DiffxAssertions {

  val matchesToAssertionName = "matchesTo"

  /**
   * Just like isTrue from zio-test, but with a different name parameter
   */
  private def assertTrue[A](actual: A): Assertion[Boolean] =
    Assertion.assertion(matchesToAssertionName)(param(actual))(identity(_))

  def matchesTo[A: Diff](expected: A)(implicit
    c: ConsoleColorConfig
  ): Assertion[A] =
    Assertion.assertionDirect(matchesToAssertionName)(param(expected)) { actual =>
      val result = Diff.compare(expected, actual)
      if (result.isIdentical) assertTrue(actual)(result.isIdentical)
      else assertTrue(result.show())(result.isIdentical)
    }
}
