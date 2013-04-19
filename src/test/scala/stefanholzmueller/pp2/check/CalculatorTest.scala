package stefanholzmueller.pp2.check

import org.testng.annotations._
import stefanholzmueller.pp2.util.Dice
import org.testng.Assert

class CalculatorTest {

	val defaultOptions = new Options(true)
	val withoutMinimumQuality = new Options(false)
	val withFesteMatrix = new Options(false, true)
	val withWildeMagie = new Options(false, true, false)
	val withSpruchhemmung = new Options(false, false, true)
	val defaultAttributes = (11, 12, 13)

	@Test
	def spectacularSuccess {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, -2, new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(3))
	}

	@Test
	def automaticSuccess {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice(1, 9, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def automaticFailure {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice(15, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def spectacularFailure {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def withoutSpruchhemmung {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 0, new Dice(10, 10, 11))
		Assert.assertEquals(outcome, Success(5, 6))
	}

	@Test
	def spruchhemmungVsSuccess {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(5, 8, 5))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spruchhemmungVsFailure {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(15, 18, 15))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spectacularSuccessVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(5))
	}

	@Test
	def spectacularFailureVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def automaticFailureVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(20, 20, 6))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def automaticSuccessVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice(18, 1, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def wildeMagie {
		val outcome = OutcomeCalculator.examine(withWildeMagie, defaultAttributes, 5, 0, new Dice(19, 5, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsWildeMagie {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 0, new Dice(19, 5, 20))
		Assert.assertEquals(outcome, Failure(15))
	}

	@Test
	def wildeMagieVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(new Options(false, true, true), defaultAttributes, 0, 0, new Dice(19, 19, 3))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def wildeMagieVsSpruchhemmungVsSpectacularFailure {
		val outcome = OutcomeCalculator.examine(new Options(false, true, true), defaultAttributes, 0, 0, new Dice(19, 19, 19))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def festeMatrix {
		val outcome = OutcomeCalculator.examine(withFesteMatrix, defaultAttributes, 0, 0, new Dice(18, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsFesteMatrix {
		val outcome = OutcomeCalculator.examine(withFesteMatrix, defaultAttributes, 0, 0, new Dice(20, 17, 20))
		Assert.assertEquals(outcome, Failure(21))
	}

	@Test
	def success_withoutPoints_noGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_smallGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def failure_withoutPoints_negativeGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice(15, 15, 15))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_withoutPoints_mixedGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice(5, 10, 15))
		Assert.assertEquals(outcome, Failure(2))
	}

	@Test
	def success_withPointsAndSameDifficulty {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 4, 4, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_zeroQuality {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, -2, new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 0, new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withPointsAndSameDifficulty_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 4, 4, new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_minimumQualityAndSmallGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, -2, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(1, 1))
	}

	@Test
	def success_withNegativePoints_noGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, 0, new Dice(1, 10, 10))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withNegativePoints_minimumQualityAndNoGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 0, new Dice(1, 10, 10))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def failure_withNegativePoints_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 0, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(1))
	}

	@Test
	def failure_withNegativePointsAndDifficulty_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 2, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(6))
	}

	@Test
	def failure_noPointsAndDifficulty_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 5, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_noPointsAndDifficulty_mixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 4, new Dice(7, 3, 16))
		Assert.assertEquals(outcome, Failure(7))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityAndGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 2, 9, new Dice(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityButGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 2, 6, new Dice(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 3))
	}

	@Test
	def success_smallPointsAndHighDifficulty_minimumQualityAndMixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, 5, new Dice(7, 3, 6))
		Assert.assertEquals(outcome, Success(1, 2))
	}

	@Test
	def success_withPoints_fullQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0, new Dice(7, 3, 6))
		Assert.assertEquals(outcome, Success(6, 10))
	}

	@Test
	def success_withPoints_mixedQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0, new Dice(13, 3, 6))
		Assert.assertEquals(outcome, Success(4, 4))
	}

	@Test
	def success_withPoints_mixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0, new Dice(13, 3, 6))
		Assert.assertEquals(outcome, Success(4, 4))
	}

	@Test
	def spectacularSuccess_negativePoints_zeroQuality {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, -5, new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(0))
	}

	@Test
	def spectacularSuccess_negativePoints_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, -5, new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(1))
	}

	@Test
	def automaticSuccess_negativeDifficulty {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, -5, new Dice(1, 9, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(3))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_cappedQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10, new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(20, 31))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_cappedQualityAndReducedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10, new Dice(13, 12, 11))
		Assert.assertEquals(outcome, Success(20, 28))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_reducedQualityAndGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10, new Dice(16, 16, 16))
		Assert.assertEquals(outcome, Success(18, 18))
	}

	@Test
	def failure_withPointsAndNegativeDifficulty {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 10, -10, new Dice(19, 19, 19))
		Assert.assertEquals(outcome, Failure(1))
	}

}
