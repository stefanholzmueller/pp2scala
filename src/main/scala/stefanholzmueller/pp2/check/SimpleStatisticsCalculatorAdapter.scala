package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple

class SimpleStatisticsCalculatorAdapter extends StatisticsGatherer {

	def gather(check: Check) = {
		val options = new Options(check.hasMinimumQuality, check.hasFesteMatrix, check.hasTollpatsch, check.hasSpruchhemmung)
		val attributes = List(check.getAttribute1, check.getAttribute2, check.getAttribute3)
		val points = check.getValue
		val difficulty = check.getDifficulty

		val statistics = SimpleStatisticsCalculator.gather(options, attributes, points, difficulty)

		statistics match {
			case Statistics(p, a) => new CheckStatistics(p, 0, a)
		}
	}

}