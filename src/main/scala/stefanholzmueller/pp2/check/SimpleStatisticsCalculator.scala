package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice

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

		Statistics(chance, average)
	}

	def main(args: Array[String]) {
		while (true) {
			val stats = gather(new Options(true, false, false, true), List(13, 11, 15), 8, 3)
			println(stats.toString)
		}
	}
}