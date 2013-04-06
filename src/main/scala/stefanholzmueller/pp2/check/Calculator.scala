package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice20
import stefanholzmueller.pp2.util.IntTriple

object Calculator extends OutcomeCalculator {

	class Options(
		val minimumEffect: Boolean,
		val festeMatrix: Boolean,
		val wildeMagie: Boolean,
		val spruchhemmung: Boolean) {

		def this(minimumEffect: Boolean, festeMatrix: Boolean) = this(minimumEffect, festeMatrix, false, false)
		def this(minimumEffect: Boolean, wildeMagie: Boolean, spruchhemmung: Boolean) = this(minimumEffect, false, wildeMagie, spruchhemmung)

		require(!(festeMatrix && (wildeMagie || spruchhemmung)))
	}

	type Attributes = (Int, Int, Int)

	def calculateResult(check: Check, diceTriple: IntTriple): CheckResult = {
		val options = new Options(check.hasMinimumQuality, check.hasFesteMatrix, check.hasTollpatsch, check.hasSpruchhemmung)
		val attributes = (check.getAttribute1, check.getAttribute2, check.getAttribute3)
		val points = check.getValue
		val difficulty = check.getDifficulty
		val dice = new Dice20(diceTriple.first, diceTriple.second, diceTriple.third)

		val outcome = examine(options, attributes, points, difficulty, dice)

		outcome match {
			case Success(q, g) => new CheckResult(CheckOutcome.SUCCESSFUL, q, g)
			case AutomaticSuccess(q) => new CheckResult(CheckOutcome.LUCKY_CHECK, q, null)
			case SpectacularSuccess(q) => new CheckResult(CheckOutcome.SPECTACULAR_SUCCESS, q, null)
			case Failure(g) => new CheckResult(CheckOutcome.UNSUCCESSFUL, null, g)
			case AutomaticFailure() => new CheckResult(CheckOutcome.FUMBLE, null, null)
			case SpectacularFailure() => new CheckResult(CheckOutcome.SPECTACULAR_FUMBLE, null, null)
			case Spruchhemmung() => new CheckResult(CheckOutcome.SPRUCHHEMMUNG, null, null)
		}
	}

	def examine(options: Options, attributes: Attributes, points: Int, difficulty: Int, dice: Dice20): Outcome = {
		specialOutcome(options, points, dice) match {
			case Some(outcome) => outcome
			case None => successOrFailure(options, attributes, points, difficulty, dice)
		}
	}

	def specialOutcome(options: Options, points: Int, dice: Dice20): Option[Outcome] = {
		if (dice.allEqualTo(1))
			Some(SpectacularSuccess(points))
		else if (dice.twoEqualTo(1))
			Some(AutomaticSuccess(points))
		else if (dice.allEqualTo(20))
			Some(SpectacularFailure())
		else if (dice.twoEqualTo(20))
			Some(AutomaticFailure())
		else if (options.spruchhemmung && dice.twoSameValues())
			Some(Spruchhemmung())
		else None
	}

	def successOrFailure(options: Options, attributes: Attributes, points: Int, difficulty: Int, dice: Dice20): Outcome = {
		Success(5, 0)
	}

}