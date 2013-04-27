package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple
import stefanholzmueller.pp2.util.Timer
import stefanholzmueller.pp2.util.Timer.time
import scala.collection.mutable.ListBuffer

class FastStatisticsCalculatorAdapter extends StatisticsGatherer {

	def gather(check: Check) = {
		val options = new Options(check.hasMinimumQuality, check.hasFesteMatrix, check.hasTollpatsch, check.hasSpruchhemmung)
		val attributes = List(check.getAttribute1, check.getAttribute2, check.getAttribute3)
		val points = check.getValue
		val difficulty = check.getDifficulty

		time("gathering statistics") {
			FastStatisticsCalculator.gather(options, attributes, points, difficulty)
		}
	}

}

object FastStatisticsCalculator {

	val MAX_PIPS = 20
	val pips = 1 to MAX_PIPS
	val total = MAX_PIPS * MAX_PIPS * MAX_PIPS

	def gather(options: Options, attributes: List[Int], points: Int, difficulty: Int) = {
		val futures = pips.par.map { die1 =>
			val successes: ListBuffer[Int] = ListBuffer()

			for (die2 <- pips; die3 <- pips) {
				val dice = new Dice(die1, die2, die3)
				val outcome = OutcomeCalculator.examine(options, attributes, points, difficulty)(dice)

				outcome match {
					case Success(q, _) => successes += q
					case AutomaticSuccess(q) => successes += q
					case SpectacularSuccess(q) => successes += q
					case _ =>
				}
			}

			successes
		}

		val qualities = futures.flatten.seq

		var successes = 0
		var quality = 0
		for (q <- qualities) {
			successes += 1
			quality += q
		}

		val chance: Double = successes / (total: Double)
		val average: Double = quality / (successes: Double)

		new Statistics(chance, average)
	}

}