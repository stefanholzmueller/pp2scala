package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple
import stefanholzmueller.pp2.util.Timer
import stefanholzmueller.pp2.util.Timer.time

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
		val (ease, effectivePoints, effectiveAttributes) = OutcomeCalculator.diceIndependentPart(attributes, points, difficulty)

		var successes = 0
		var quality = 0

		for (die1 <- pips; die2 <- pips; die3 <- pips) {
			val dice = new Dice(die1, die2, die3)
			val outcome = OutcomeCalculator.specialOutcome(options, points, dice) match {
				case Some(special) => special
				case None => OutcomeCalculator.successOrFailureInternal(options, points, dice, ease, effectivePoints, effectiveAttributes)
			}

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