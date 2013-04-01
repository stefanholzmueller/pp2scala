package stefanholzmueller.pp2.util

import org.testng.annotations._
import org.hamcrest.MatcherAssert._
import org.hamcrest.Matchers._

class ThreeD20Test {

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues1 {
		new ThreeD20(1, 2, -3)
	}

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues2 {
		new ThreeD20(111, 2, 3)
	}

	@Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
	def requireSaneValues3 {
		new ThreeD20(0, Int.MaxValue, Int.MinValue)
	}

	@Test
	def applyReturnsDice {
		val dice = new ThreeD20(1, 2, 3)

		assertThat(dice(0), is(1))
		assertThat(dice(1), is(2))
		assertThat(dice(2), is(3))
	}

	@Test
	def allEqualTo {
		assertThat(new ThreeD20(3, 3, 3).allEqualTo(1), is(false))
		assertThat(new ThreeD20(1, 2, 3).allEqualTo(1), is(false))
		assertThat(new ThreeD20(1, 1, 1).allEqualTo(1), is(true))
	}

	@Test
	def twoEqualTo {
		assertThat(new ThreeD20(3, 3, 3).twoEqualTo(1), is(false))
		assertThat(new ThreeD20(1, 2, 3).twoEqualTo(1), is(false))
		assertThat(new ThreeD20(1, 1, 2).twoEqualTo(1), is(true))
		assertThat(new ThreeD20(1, 1, 1).twoEqualTo(1), is(true))
	}

	@Test
	def twoSameValues {
		assertThat(new ThreeD20(1, 2, 3).twoSameValues(), is(false))
		assertThat(new ThreeD20(1, 1, 2).twoSameValues(), is(true))
		assertThat(new ThreeD20(1, 1, 1).twoSameValues(), is(true))
	}

}