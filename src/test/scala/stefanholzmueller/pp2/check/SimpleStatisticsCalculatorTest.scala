package stefanholzmueller.pp2.check

import org.testng.annotations.Test
import org.testng.Assert

class SimpleStatisticsCalculatorTest {

	@Test
	def defaultCheck {
		val statistics = SimpleStatisticsCalculator.gather(Options.default, List(12, 12, 12), 4, 0)
		Assert.assertEquals(statistics, Statistics(0.461, 2.7838937093275486))
	}

	@Test
	def withoutMinimumQuality {
		val statistics = SimpleStatisticsCalculator.gather(Options.nothing, List(12, 12, 12), 4, 0)
		Assert.assertEquals(statistics, Statistics(0.461, 2.6374728850325377))
	}

	@Test
	def maximumSuccess {
		val statistics = SimpleStatisticsCalculator.gather(Options.nothing, List(20, 20, 20), 18, 0)
		Assert.assertEquals(statistics, Statistics(0.99275, 18))
	}

	@Test
	def minimumSuccess {
		val statistics = SimpleStatisticsCalculator.gather(Options.nothing, List(10, 10, 10), 0, 10)
		Assert.assertEquals(statistics, Statistics(0.00725, 0))
	}

	@Test
	def minimumSuccess_withMinimumQuality {
		val statistics = SimpleStatisticsCalculator.gather(Options.default, List(10, 10, 10), 0, 10)
		Assert.assertEquals(statistics, Statistics(0.00725, 1))
	}

	@Test
	def minimumSuccess_withOverwhelmingDifficulty {
		val statistics = SimpleStatisticsCalculator.gather(Options.default, List(12, 12, 12), 4, 20)
		Assert.assertEquals(statistics, Statistics(0.00725, 4))
	}

	@Test
	def maximumSuccess_causedByNegativeDifficulty {
		val statistics = SimpleStatisticsCalculator.gather(Options.default, List(12, 12, 12), 4, -24)
		Assert.assertEquals(statistics, Statistics(0.99275, 4))
	}
}