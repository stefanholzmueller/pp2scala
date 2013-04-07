package stefanholzmueller.pp2.util

import org.testng.annotations._
import org.hamcrest.MatcherAssert._
import org.hamcrest.Matchers._

class Dice20Test {

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues1 {
		new Dice20(1, 2, -3)
	}

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues2 {
		new Dice20(111, 2, 3)
	}

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues3 {
		new Dice20(0, Int.MaxValue, Int.MinValue)
	}

	@Test
	def applyReturnsDice {
		val dice = new Dice20(1, 2, 3)

		assertThat(dice(0), is(1))
		assertThat(dice(1), is(2))
		assertThat(dice(2), is(3))
	}

	@Test
	def allEqualTo {
		assertThat(new Dice20(3, 3, 3).allEqualTo(1), is(false))
		assertThat(new Dice20(1, 2, 3).allEqualTo(1), is(false))
		assertThat(new Dice20(1, 1, 1).allEqualTo(1), is(true))
	}

	@Test
	def twoEqualTo {
		assertThat(new Dice20(3, 3, 3).twoEqualTo(1), is(false))
		assertThat(new Dice20(1, 2, 3).twoEqualTo(1), is(false))
		assertThat(new Dice20(1, 1, 2).twoEqualTo(1), is(true))
		assertThat(new Dice20(1, 1, 1).twoEqualTo(1), is(true))
	}

	@Test
	def twoSameValues {
		assertThat(new Dice20(1, 2, 3).twoSameValues(), is(false))
		assertThat(new Dice20(1, 1, 2).twoSameValues(), is(true))
		assertThat(new Dice20(1, 1, 1).twoSameValues(), is(true))
	}

	@Test
	def sum {
		assertThat(new Dice20(1, 2, 3).sum, is(6))
	}

	@Test
	def compareWithAttributes {
		val diff = new Dice20(1, 12, 20).compareWithAttributes(List(11, 12, 13))
		assertThat(diff(0), is(-10))
		assertThat(diff(1), is(0))
		assertThat(diff(2), is(7))
	}

}