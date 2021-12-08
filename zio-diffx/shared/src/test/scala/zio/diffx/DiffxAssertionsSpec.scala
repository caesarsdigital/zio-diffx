package zio.diffx

import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx._

import java.io.IOException

import zio._
import zio.console._
import zio.test.Assertion._
import zio.test._
import zio.test.environment._
import com.softwaremill

object DiffxAssertionsSpec extends DefaultRunnableSpec with DiffxAssertions {

  sealed trait FBZ
  final case class Foo(foo: Int)                        extends FBZ
  final case class Bar(bar: String)                     extends FBZ
  final case class Baz(foo: Foo, bar: Bar, baz: Double) extends FBZ

  val baz1 = Baz(Foo(3), Bar("bar"), 3.14)
  val baz2 = Baz(Foo(3), Bar("bar"), 3.14)

  val baz3 = Baz(Foo(4), Bar("bar"), 3.14)
  val baz4 = Baz(Foo(3), Bar("barz"), 3.14)
  val baz5 = Baz(Foo(3), Bar("bar"), 3.141)

  val baz6 = Baz(Foo(4), Bar("barz"), 3.141)

  def spec: ZSpec[Environment, Failure] = suite("HelloWorldSpec")(
    test("Diffx Assertions work as expected on nested classes: matching") {
      assert(baz2)(matchesTo(baz1))
    },
    test("Diffx Assertions work as expected on nested classes: not matching") {
      assert(baz3)(matchesTo(baz1))
    }
  )
}
