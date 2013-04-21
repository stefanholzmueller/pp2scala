package stefanholzmueller.pp2.check

import org.testng.annotations.Test
import org.testng.Assert

class SimpleStatisticsCalculatorTest {

	@Test
	def defaultCheck {
		val statistics = gatherStatistics(Options.default, List(12, 12, 12), 4, 0)
		Assert.assertEquals(statistics, Statistics(0.461, 2.7838937093275486))
	}

	@Test
	def withoutMinimumQuality {
		val statistics = gatherStatistics(Options.nothing, List(12, 12, 12), 4, 0)
		Assert.assertEquals(statistics, Statistics(0.461, 2.6374728850325377))
	}

	@Test
	def maximumSuccess {
		val statistics = gatherStatistics(Options.nothing, List(20, 20, 20), 18, 0)
		Assert.assertEquals(statistics, Statistics(0.99275, 18))
	}

	@Test
	def minimumSuccess {
		val statistics = gatherStatistics(Options.nothing, List(10, 10, 10), 0, 10)
		Assert.assertEquals(statistics, Statistics(0.00725, 0))
	}

	@Test
	def minimumSuccess_withMinimumQuality {
		val statistics = gatherStatistics(Options.default, List(10, 10, 10), 0, 10)
		Assert.assertEquals(statistics, Statistics(0.00725, 1))
	}

	@Test
	def minimumSuccess_withOverwhelmingDifficulty {
		val statistics = gatherStatistics(Options.default, List(12, 12, 12), 4, 20)
		Assert.assertEquals(statistics, Statistics(0.00725, 4))
	}

	@Test
	def maximumSuccess_causedByNegativeDifficulty {
		val statistics = gatherStatistics(Options.default, List(12, 12, 12), 4, -24)
		Assert.assertEquals(statistics, Statistics(0.99275, 4))
	}

	@Test
	def defaultCheck_withSpruchhemmung {
		val statistics = gatherStatistics(new Options(true, false, false, true), List(12, 12, 12), 4, 0)
		Assert.assertEquals(statistics, Statistics(0.38825, 2.713457823567289))
	}

	@Test
	def maximumSuccess_withWildeMagie {
		val statistics = gatherStatistics(new Options(true, false, true, false), List(20, 20, 20), 18, 0)
		Assert.assertEquals(statistics, Statistics(0.972, 18))
	}

	@Test
	def maximumSuccess_withFesteMatrix {
		val statistics = gatherStatistics(new Options(true, true, false, false), List(20, 20, 20), 18, 0)
		Assert.assertEquals(statistics, Statistics(0.999125, 18))
	}

	private def gatherStatistics(options: Options, attributes: List[Int], points: Int, difficulty: Int) =
		SimpleStatisticsCalculator.gather(options, attributes, points, difficulty)

}