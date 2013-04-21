package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple

object OutcomeCalculator {

	def examine(options: Options, attributes: List[Int], points: Int, difficulty: Int)(dice: Dice): Outcome = {
		require(options != null, "options must not be null")
		require(attributes != null && attributes.length == 3, "attributes must be a list of length 3")
		require(dice != null, "dice must not be null")

		specialOutcome(options, points, dice) match {
			case Some(special) => special
			case None => successOrFailure(options, attributes, points, difficulty, dice)
		}
	}

	def specialOutcome(options: Options, points: Int, dice: Dice): Option[Outcome] = {
		val quality = applyMinimumQuality(options, points)

		if (dice.allEqualTo(1))
			Some(SpectacularSuccess(quality))
		else if (dice.twoEqualTo(1))
			Some(AutomaticSuccess(quality))
		else if (dice.allEqualTo(20))
			Some(SpectacularFailure())
		else if (options.wildeMagie && dice.twoGreaterThan(18))
			Some(AutomaticFailure())
		else if (options.festeMatrix && dice.twoEqualTo(20) && dice.sum > 57)
			Some(AutomaticFailure())
		else if (!options.festeMatrix && dice.twoEqualTo(20))
			Some(AutomaticFailure())
		else if (options.spruchhemmung && dice.twoSameValues())
			Some(Spruchhemmung())
		else None
	}

	private def successOrFailure(options: Options, attributes: List[Int], points: Int, difficulty: Int, dice: Dice): Outcome = {
		val ease = points - difficulty
		val effectivePoints = ease max 0
		val effectiveAttributes = attributes.map(_ + (ease min 0))
		val comparisons = dice.compareWithAttributes(effectiveAttributes)
		val usedPoints = comparisons.filter(_ > 0).sum

		if (usedPoints > effectivePoints) {
			Failure(usedPoints - effectivePoints)
		} else {
			val worstDie = comparisons.reduce(_ max _)
			if (ease < 0) { // effectivePoints == 0  // points < difficulty
				val quality = applyMinimumQuality(options, 0)
				Success(quality, -worstDie)
			} else {
				if (usedPoints == 0) {
					val rawQuality = points min ease // points min (points - difficulty)
					val quality = applyMinimumQuality(options, rawQuality)
					Success(quality, ease - worstDie)
				} else {
					val leftoverPoints = ease - usedPoints // points - difficulty - usedPoints
					val quality = applyMinimumQuality(options, leftoverPoints min points)
					if (leftoverPoints >= points) { // difficulty + usedPoints <= 0
						Success(quality, leftoverPoints - (worstDie min 0))
					} else {
						Success(quality, leftoverPoints)
					}
				}
			}
		}
	}

	private def applyMinimumQuality(options: Options, rawQuality: Int): Int = {
		if (options.minimumQuality) rawQuality max 1 else rawQuality max 0
	}

}