package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple
import stefanholzmueller.pp2.util.Timer
import stefanholzmueller.pp2.util.Timer.time

class SimpleStatisticsCalculatorAdapter extends StatisticsGatherer {

	def gather(check: Check) = {
		val (options, attributes, points, difficulty) = OutcomeCalculator.javaCheckToscalaTuple(check)

		time("gathering statistics") {
			SimpleStatisticsCalculator.gather(options, attributes, points, difficulty)
		}
	}

}

object SimpleStatisticsCalculator {

	val MAX_PIPS = 20
	val pips = 1 to MAX_PIPS
	val total = MAX_PIPS * MAX_PIPS * MAX_PIPS

	def gather(options: Options, attributes: List[Int], points: Int, difficulty: Int) = {
		val examiner = OutcomeCalculator.examine(options, attributes, points, difficulty)_

		var successes = 0
		var quality = 0

		for (die1 <- pips; die2 <- pips; die3 <- pips) {
			val outcome = examiner(new Dice(die1, die2, die3))

			if (outcome.isSuccessful) {
				successes += 1
				quality += (outcome match {
					case Success(q, _) => q
					case AutomaticSuccess(q) => q
					case SpectacularSuccess(q) => q
					case _ => 0
				})
			}
		}

		val chance: Double = successes / (total: Double)
		val average: Double = quality / (successes: Double)

		new Statistics(chance, average)
	}

}