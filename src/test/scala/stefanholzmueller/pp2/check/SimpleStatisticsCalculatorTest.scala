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
}