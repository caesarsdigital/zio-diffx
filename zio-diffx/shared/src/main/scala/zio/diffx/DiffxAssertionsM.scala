package zio.diffx

import com.softwaremill.diffx.{ConsoleColorConfig, Diff}
import zio.UIO
import zio.test.AssertionM.Render.*
import zio.test.*

trait DiffxAssertionsM {

  val matchesToAssertionName = "matchesTo"

  /**
   * Just like isTrue from zio-test, but with a different name parameter
   */
  private def assertTrue[A](actual: UIO[A]): AssertionM[Boolean] =
    AssertionM.assertionM(matchesToAssertionName)(param(actual))(identity(UIO(_)))

  def matchesTo[A: Diff](expected: A)(implicit
    c: ConsoleColorConfig
  ): AssertionM[A] =
    AssertionM.assertionDirect(matchesToAssertionName)(param(expected)) { actual =>
      val result = Diff.compare(expected, actual)
      assertTrue(UIO(actual)).label(result.show()).runM(result.isIdentical)
    }
}