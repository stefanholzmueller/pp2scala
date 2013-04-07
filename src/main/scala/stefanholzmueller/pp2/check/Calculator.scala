package stefanholzmueller.pp2.check

import stefanholzmueller.pp2.util.Dice20
import stefanholzmueller.pp2.util.IntTriple

object Calculator extends OutcomeCalculator {

	class Options(
		val minimumQuality: Boolean,
		val festeMatrix: Boolean,
		val wildeMagie: Boolean,
		val spruchhemmung: Boolean) {

		def this(mq: Boolean, fm: Boolean) = this(mq, fm, false, false)
		def this(mq: Boolean, wm: Boolean, sh: Boolean) = this(mq, false, wm, sh)

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
			case Some(special) => special
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

	def successOrFailure(options: Options, attributes: Attributes, points: Int, difficulty: Int, dice: Dice20): Outcome = {
		val effectivePoints = points - difficulty // TODO quality <- points
		if (effectivePoints > 0) {
			successOrFailureWithPoints
		} else {
			val attributeList = List(attributes._1, attributes._2, attributes._3)
			val effectiveAttributes = attributeList.map(_ + effectivePoints)
			val diff = dice.compareWithAttributes(effectiveAttributes)

			val exceeded = diff.fold(0)((acc, x) => if (x > 0) acc + x else acc)
			if (exceeded > 0) {
				Failure(exceeded)
			} else {
				val possibleDifficulty = diff.reduce(_ max _)
				val quality = if (options.minimumQuality) 1 else 0
				Success(quality, -possibleDifficulty)
			}
		}
	}

	private def successOrFailureWithPoints = {
		//			val usedPoints = diff.fold(0)((a, x) => if (x > 0) a + x else a)
		Success(5, 0) // TODO
	}

}