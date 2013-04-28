package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple

class OutcomeCalculatorAdapter extends OutcomeExaminer {

	def examine(check: Check, diceTriple: IntTriple): Outcome = {
		val options = new Options(check.hasMinimumQuality, check.hasFesteMatrix, check.hasWildeMagie, check.hasSpruchhemmung)
		val attributes = List(check.getAttribute1, check.getAttribute2, check.getAttribute3)
		val points = check.getValue
		val difficulty = check.getDifficulty
		val dice = new Dice(diceTriple.first, diceTriple.second, diceTriple.third)

		OutcomeCalculator.examine(options, attributes, points, difficulty)(dice)
	}

}

object OutcomeCalculator {

	def examine(options: Options, attributes: List[Int], points: Int, difficulty: Int)(dice: Dice): Outcome = {
		specialOutcome(options, points, dice) match {
			case Some(special) => special
			case None => successOrFailure(options, attributes, points, difficulty, dice)
		}
	}

	private def specialOutcome(options: Options, points: Int, dice: Dice): Option[Outcome] = {
		if (dice.allEqualTo(1))
			Some(SpectacularSuccess(applyMinimumQuality(options, points)))
		else if (dice.twoEqualTo(1))
			Some(AutomaticSuccess(applyMinimumQuality(options, points)))
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

	private def successOrFailure(options: Options, attributes: List[Int], points: Int, difficulty: Int, dice: Dice) = {
		val (ease, effectivePoints, effectiveAttributes) = diceIndependentPart(attributes, points, difficulty)
		successOrFailureInternal(options, points, dice, ease, effectivePoints, effectiveAttributes)
	}

	private def diceIndependentPart(attributes: List[Int], points: Int, difficulty: Int): (Int, Int, List[Int]) = {
		val ease = points - difficulty
		val effectivePoints = ease max 0
		val effectiveAttributes = if (ease < 0) attributes.map(_ + ease) else attributes

		(ease, effectivePoints, effectiveAttributes)
	}

	private def successOrFailureInternal(options: Options, points: Int, dice: Dice,
		ease: Int, effectivePoints: Int, effectiveAttributes: List[Int]) = {

		val comparisons = dice.compareWithAttributes(effectiveAttributes)
		val usedPoints = comparisons.filter(_ > 0).sum

		if (usedPoints > effectivePoints) {
			Failure(usedPoints - effectivePoints)
		} else if (usedPoints == 0) {
			val quality = applyMinimumQuality(options, effectivePoints min points)
			val worstDie = comparisons.reduce(_ max _) // is <= 0 in this case
			Success(quality, effectivePoints - worstDie)
		} else {
			val leftoverPoints = ease - usedPoints
			val quality = applyMinimumQuality(options, leftoverPoints min points)
			Success(quality, leftoverPoints)
		}
	}

	private def applyMinimumQuality(options: Options, rawQuality: Int): Int = {
		if (options.minimumQuality) rawQuality max 1 else rawQuality max 0
	}

}