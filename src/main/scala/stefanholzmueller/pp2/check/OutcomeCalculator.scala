package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple

object OutcomeCalculator {

	type Attributes = (Int, Int, Int)

	def examine(options: Options, attributes: Attributes, points: Int, difficulty: Int, dice: Dice): Outcome = {
		specialOutcome(options, points, dice) match {
			case Some(special) => special
			case None => successOrFailure(options, attributes, points, difficulty, dice)
		}
	}

	private def specialOutcome(options: Options, points: Int, dice: Dice): Option[Outcome] = {
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

	private def successOrFailure(options: Options, attributes: Attributes, points: Int, difficulty: Int, dice: Dice): Outcome = {
		val effectivePoints = points - difficulty
		val attributeList = List(attributes._1, attributes._2, attributes._3)

		if (effectivePoints < 0) {
			val effectiveAttributes = attributeList.map(_ + effectivePoints)
			val comparisons = dice.compareWithAttributes(effectiveAttributes)
			val usedPoints = comparisons.filter(_ > 0).sum
			if (usedPoints > 0) {
				Failure(usedPoints)
			} else {
				val quality = applyMinimumQuality(options, 0)
				val worstDie = comparisons.reduce(_ max _)
				Success(quality, -worstDie)
			}
		} else {
			val comparisons = dice.compareWithAttributes(attributeList)
			val usedPoints = comparisons.filter(_ > 0).sum
			if (usedPoints > effectivePoints) {
				Failure(usedPoints - effectivePoints)
			} else {
				if (usedPoints == 0) {
					val rawQuality = points min effectivePoints
					val quality = applyMinimumQuality(options, rawQuality)
					val worstDie = comparisons.reduce(_ max _)
					Success(quality, effectivePoints - worstDie)
				} else {
					val leftoverPoints = effectivePoints - usedPoints
					if (leftoverPoints >= points) {
						val quality = applyMinimumQuality(options, points)
						val worstDie = comparisons.reduce(_ max _) min 0
						Success(quality, leftoverPoints - worstDie)
					} else {
						val quality = applyMinimumQuality(options, leftoverPoints)
						Success(quality, quality)
					}
				}
			}
		}
	}

	private def applyMinimumQuality(options: Options, rawQuality: Int): Int = {
		if (options.minimumQuality) rawQuality max 1 else rawQuality max 0
	}

}