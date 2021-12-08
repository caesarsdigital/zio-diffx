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

  def matchesTo[A: Diff](expected: A)(implicit c: ConsoleColorConfig): Assertion[A] =
    Assertion.assertionDirect(matchesToAssertionName)(param(expected)) { actual =>
      val result = Diff.compare(expected, actual)
      assertTrue(actual).label(result.show())(result.isIdentical)
    }
}
