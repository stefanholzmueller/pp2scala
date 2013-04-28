package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple
import stefanholzmueller.pp2.util.Timer
import stefanholzmueller.pp2.util.Timer.time
import scala.collection.mutable.ListBuffer

class ParallelStatisticsCalculatorAdapter extends StatisticsGatherer {

	def gather(check: Check) = {
		val (options, attributes, points, difficulty) = OutcomeCalculator.javaCheckToscalaTuple(check)

		time("gathering statistics in parallel") {
			ParallelStatisticsCalculator.gather(options, attributes, points, difficulty)
		}
	}

}

object ParallelStatisticsCalculator {

	val MAX_PIPS = 20
	val pips = 1 to MAX_PIPS
	val total = MAX_PIPS * MAX_PIPS * MAX_PIPS

	def gather(options: Options, attributes: List[Int], points: Int, difficulty: Int) = {
		val parallelQualities = pips.par.map { die1 =>
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

		val qualities = parallelQualities.flatten.seq

		val chance: Double = qualities.size / (total: Double)
		val average: Double = qualities.sum / (qualities.size: Double)

		new Statistics(chance, average)
	}

}