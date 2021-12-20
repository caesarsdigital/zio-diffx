package zio.diffx

import com.softwaremill.diffx.generic.auto.*
import zio.test.Assertion.not
import zio.test.*
object DiffxAssertionsSpec extends DefaultRunnableSpec with DiffxAssertions {

  sealed trait FBZ
  final case class Foo(foo: Int)                        extends FBZ
  final case class Bar(bar: String)                     extends FBZ
  final case class Baz(foo: Foo, bar: Bar, baz: Double) extends FBZ

  val baz1: Baz = Baz(Foo(3), Bar("bar"), 3.14)
  val baz2: Baz = Baz(Foo(3), Bar("bar"), 3.14)

  val baz3: Baz = Baz(Foo(4), Bar("bar"), 3.14)
  val baz4: Baz = Baz(Foo(3), Bar("barz"), 3.14)
  val baz5: Baz = Baz(Foo(3), Bar("bar"), 3.141)

  val baz6: Baz = Baz(Foo(4), Bar("barz"), 3.141)

  def spec: ZSpec[Environment, Failure] = suite("HelloWorldSpec")(
    test("Diffx Assertions work as expected on nested classes: matching") {
      assert(baz2)(matchesTo(baz1))
    },
    test("Diffx Assertions work as expected on nested classes: not matching") {
      // Remove the `not` calls to see the diffx error messages
      assert(baz3)(not(matchesTo(baz1)))
      && assert(baz4)(not(matchesTo(baz1)))
      && assert(baz5)(not(matchesTo(baz1)))
      && assert(baz6)(not(matchesTo(baz1)))
    }
  )
}
