package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice
import stefanholzmueller.pp2.util.IntTriple

class CalculatorAdapter extends OutcomeExaminer {

	def examine(check: Check, diceTriple: IntTriple): CheckResult = {
		val options = new Options(check.hasMinimumQuality, check.hasFesteMatrix, check.hasTollpatsch, check.hasSpruchhemmung)
		val attributes = (check.getAttribute1, check.getAttribute2, check.getAttribute3)
		val points = check.getValue
		val difficulty = check.getDifficulty
		val dice = new Dice(diceTriple.first, diceTriple.second, diceTriple.third)

		val outcome = OutcomeCalculator.examine(options, attributes, points, difficulty)(dice)

		outcome match {
			case Success(q, g) => new CheckResult(CheckOutcome.SUCCESSFUL, q, g)
			case AutomaticSuccess(q) => new CheckResult(CheckOutcome.LUCKY_CHECK, q, null)
			case SpectacularSuccess(q) => new CheckResult(CheckOutcome.SPECTACULAR_SUCCESS, q, null)
			case Failure(g) => new CheckResult(CheckOutcome.UNSUCCESSFUL, null, -g) // note the negation
			case AutomaticFailure() => new CheckResult(CheckOutcome.FUMBLE, null, null)
			case SpectacularFailure() => new CheckResult(CheckOutcome.SPECTACULAR_FUMBLE, null, null)
			case Spruchhemmung() => new CheckResult(CheckOutcome.SPRUCHHEMMUNG, null, null)
		}
	}

}