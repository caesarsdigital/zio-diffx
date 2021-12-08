package zio.diffx

import com.softwaremill.diffx.generic.auto.*
import zio.UIO
import zio.test.AssertionM.not
import zio.test.*
object DiffxAssertionsSpecM extends DefaultRunnableSpec with DiffxAssertionsM {

  sealed trait FBZ
  final case class Foo(foo: Int)                        extends FBZ
  final case class Bar(bar: String)                     extends FBZ
  final case class Baz(foo: Foo, bar: Bar, baz: Double) extends FBZ

  val baz1: Baz          = Baz(Foo(3), Bar("bar"), 3.14)
  val baz2: zio.UIO[Baz] = UIO(Baz(Foo(3), Bar("bar"), 3.14))

  val baz3: zio.UIO[Baz] = UIO(Baz(Foo(4), Bar("bar"), 3.14))
  val baz4: zio.UIO[Baz] = UIO(Baz(Foo(3), Bar("barz"), 3.14))
  val baz5: zio.UIO[Baz] = UIO(Baz(Foo(3), Bar("bar"), 3.141))

  val baz6: zio.UIO[Baz] = UIO(Baz(Foo(4), Bar("barz"), 3.141))

  def spec: ZSpec[Environment, Failure] = suite("HelloWorldSpec")(
    testM("Diffx Assertions work as expected on nested classes: matching") {
      assertM(baz2)(matchesTo(baz1))
    },
    testM("Diffx Assertions work as expected on nested classes: not matching") {
      for {
        a1 <- assertM(baz3)(not(matchesTo(baz1)))
        a2 <- assertM(baz4)(not(matchesTo(baz1)))
        a3 <- assertM(baz5)(not(matchesTo(baz1)))
        a4 <- assertM(baz6)(not(matchesTo(baz1)))
      } yield (a1 && a2 && a3 && a4)
    }
  )
}
