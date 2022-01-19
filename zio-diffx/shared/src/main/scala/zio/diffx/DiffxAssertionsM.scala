package zio.diffx

import com.softwaremill.diffx.Diff
import zio.UIO
import zio.test.AssertionM.Render.*
import zio.test.*

trait DiffxAssertionsM {

  val matchesToAssertionNameM = "matchesTo"

  /**
   * Just like isTrue from zio-test, but with a different name parameter
   */
  private def assertTrueM[A](actual: UIO[A]): AssertionM[Boolean] =
    AssertionM.assertionM(matchesToAssertionNameM)(param(actual))(identity(UIO(_)))

  def matchesToM[A: Diff](expected: A): AssertionM[A] =
    AssertionM.assertionDirect(matchesToAssertionNameM)(param(expected)) { actual =>
      val result = Diff.compare(expected, actual)
      assertTrueM(UIO(actual)).label(result.show()).runM(result.isIdentical)
    }
}
