package stefanholzmueller.pp2.check

import org.testng.annotations._
import stefanholzmueller.pp2.util.Dice
import org.testng.Assert

class OutcomeCalculatorTest {

	val defaultOptions = new Options(true)
	val withoutMinimumQuality = new Options(false)
	val withFesteMatrix = new Options(false, true)
	val withWildeMagie = new Options(false, true, false)
	val withSpruchhemmung = new Options(false, false, true)
	val defaultAttributes = List(11, 12, 13)

	@Test
	def spectacularSuccess {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, -2)(new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(3))
	}

	@Test
	def automaticSuccess {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5)(new Dice(1, 9, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def automaticFailure {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5)(new Dice(15, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def spectacularFailure {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 5)(new Dice(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def withoutSpruchhemmung {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 5, 0)(new Dice(10, 10, 11))
		Assert.assertEquals(outcome, Success(5, 6))
	}

	@Test
	def spruchhemmungVsSuccess {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(5, 8, 5))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spruchhemmungVsFailure {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(15, 18, 15))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spectacularSuccessVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(5))
	}

	@Test
	def spectacularFailureVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def automaticFailureVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(20, 20, 6))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def automaticSuccessVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(withSpruchhemmung, defaultAttributes, 5, 0)(new Dice(18, 1, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def wildeMagie {
		val outcome = OutcomeCalculator.examine(withWildeMagie, defaultAttributes, 5, 0)(new Dice(19, 5, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsWildeMagie {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 0)(new Dice(19, 5, 20))
		Assert.assertEquals(outcome, Failure(15))
	}

	@Test
	def wildeMagieVsSpruchhemmung {
		val outcome = OutcomeCalculator.examine(new Options(false, true, true), defaultAttributes, 0, 0)(new Dice(19, 19, 3))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def wildeMagieVsSpruchhemmungVsSpectacularFailure {
		val outcome = OutcomeCalculator.examine(new Options(false, true, true), defaultAttributes, 0, 0)(new Dice(19, 19, 19))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def festeMatrix {
		val outcome = OutcomeCalculator.examine(withFesteMatrix, defaultAttributes, 0, 0)(new Dice(18, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsFesteMatrix {
		val outcome = OutcomeCalculator.examine(withFesteMatrix, defaultAttributes, 0, 0)(new Dice(20, 17, 20))
		Assert.assertEquals(outcome, Failure(21))
	}

	@Test
	def success_withoutPoints_noGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0)(new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_smallGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def failure_withoutPoints_negativeGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0)(new Dice(15, 15, 15))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_withoutPoints_mixedGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0)(new Dice(5, 10, 15))
		Assert.assertEquals(outcome, Failure(2))
	}

	@Test
	def success_withPointsAndSameDifficulty {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 4, 4)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_zeroQuality {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, -2)(new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 0)(new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withPointsAndSameDifficulty_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 4, 4)(new Dice(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_minimumQualityAndSmallGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, -2)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(1, 1))
	}

	@Test
	def success_withNegativePoints_noGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, 0)(new Dice(1, 10, 10))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withNegativePoints_minimumQualityAndNoGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 0)(new Dice(1, 10, 10))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def failure_withNegativePoints_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 0)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(1))
	}

	@Test
	def failure_withNegativePointsAndDifficulty_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, 2)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(6))
	}

	@Test
	def failure_noPointsAndDifficulty_negativeGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 5)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_noPointsAndDifficulty_mixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 4)(new Dice(7, 3, 16))
		Assert.assertEquals(outcome, Failure(7))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityAndGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 2, 9)(new Dice(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityButGap {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 2, 6)(new Dice(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 3))
	}

	@Test
	def success_smallPointsAndHighDifficulty_minimumQualityAndMixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, 5)(new Dice(7, 3, 6))
		Assert.assertEquals(outcome, Success(1, 2))
	}

	@Test
	def success_withPoints_fullQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0)(new Dice(7, 3, 6))
		Assert.assertEquals(outcome, Success(6, 10))
	}

	@Test
	def success_withPoints_mixedQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0)(new Dice(13, 3, 6))
		Assert.assertEquals(outcome, Success(4, 4))
	}

	@Test
	def success_withPoints_mixedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 6, 0)(new Dice(13, 3, 6))
		Assert.assertEquals(outcome, Success(4, 4))
	}

	@Test
	def spectacularSuccess_negativePoints_zeroQuality {
		val outcome = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, -2, -5)(new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(0))
	}

	@Test
	def spectacularSuccess_negativePoints_minimumQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -2, -5)(new Dice(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(1))
	}

	@Test
	def automaticSuccess_negativeDifficulty {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 3, -5)(new Dice(1, 9, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(3))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_cappedQuality {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10)(new Dice(10, 10, 10))
		Assert.assertEquals(outcome, Success(20, 31))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_cappedQualityAndReducedGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10)(new Dice(13, 12, 11))
		Assert.assertEquals(outcome, Success(20, 28))
	}

	@Test
	def success_withPointsAndNegativeDifficulty_reducedQualityAndGap {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 20, -10)(new Dice(16, 16, 16))
		Assert.assertEquals(outcome, Success(18, 18))
	}

	@Test
	def failure_withPointsAndNegativeDifficulty {
		val outcome = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 10, -10)(new Dice(19, 19, 19))
		Assert.assertEquals(outcome, Failure(1))
	}

	@Test
	def scenario_zeroPoints_zeroDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 0)_
		Assert.assertEquals(partial(new Dice(5, 6, 3)), Success(1, 6))
		Assert.assertEquals(partial(new Dice(11, 11, 8)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(11, 13, 8)), Failure(1))
		Assert.assertEquals(partial(new Dice(14, 13, 12)), Failure(4))
		Assert.assertEquals(partial(new Dice(4, 2, 20)), Failure(7))
	}

	@Test
	def scenario_negativePoints_zeroDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -3, 0)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 3))
		Assert.assertEquals(partial(new Dice(1, 9, 10)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(2, 10, 4)), Failure(1))
		Assert.assertEquals(partial(new Dice(11, 12, 13)), Failure(9))
		Assert.assertEquals(partial(new Dice(4, 2, 20)), Failure(10))
	}

	@Test
	def scenario_smallPoints_zeroDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 4, 0)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(4, 4 + 6))
		Assert.assertEquals(partial(new Dice(1, 9, 10)), Success(4, 4 + 3))
		Assert.assertEquals(partial(new Dice(11, 10, 4)), Success(4, 4 + 0))
		Assert.assertEquals(partial(new Dice(12, 13, 14)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(15, 5, 5)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(4, 2, 20)), Failure(3))
		Assert.assertEquals(partial(new Dice(14, 14, 13)), Failure(1))
	}

	@Test
	def scenario_hugePoints_zeroDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 24, 0)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(24, 24 + 6))
		Assert.assertEquals(partial(new Dice(11, 10, 4)), Success(24, 24 + 0))
		Assert.assertEquals(partial(new Dice(15, 13, 16)), Success(16, 16 + 0))
		Assert.assertEquals(partial(new Dice(19, 18, 13)), Success(10, 10))
	}

	@Test
	def scenario_noPoints_smallDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, 2)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 4))
		Assert.assertEquals(partial(new Dice(6, 8, 11)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(10, 11, 4)), Failure(2))
		Assert.assertEquals(partial(new Dice(4, 3, 18)), Failure(7))
	}

	@Test
	def scenario_noPoints_negativeDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 0, -5)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 11))
		Assert.assertEquals(partial(new Dice(16, 8, 11)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(10, 19, 4)), Failure(2))
		Assert.assertEquals(partial(new Dice(18, 18, 18)), Failure(13))
	}

	@Test
	def scenario_negativePoints_smallerNegativeDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -3, -5)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 6 + 2))
		Assert.assertEquals(partial(new Dice(7, 13, 11)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(7, 14, 11)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(7, 15, 11)), Failure(1))
		Assert.assertEquals(partial(new Dice(7, 15, 15)), Failure(3))
	}

	@Test
	def scenario_negativePoints_biggerNegativeDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, -3, -1)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 4))
		Assert.assertEquals(partial(new Dice(7, 10, 11)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(7, 11, 11)), Failure(1))
		Assert.assertEquals(partial(new Dice(7, 15, 15)), Failure(9))
	}

	@Test
	def scenario_smallPoints_negativeDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 2, -5)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(2, 2 + 5 + 6))
		Assert.assertEquals(partial(new Dice(5, 7, 5)), Success(2, 2 + 5 + 5))
		Assert.assertEquals(partial(new Dice(5, 8, 5)), Success(2, 2 + 5 + 4))
		Assert.assertEquals(partial(new Dice(5, 9, 5)), Success(2, 2 + 5 + 3))
		Assert.assertEquals(partial(new Dice(5, 10, 5)), Success(2, 2 + 5 + 2))
		Assert.assertEquals(partial(new Dice(5, 11, 5)), Success(2, 2 + 5 + 1))
		Assert.assertEquals(partial(new Dice(5, 12, 5)), Success(2, 2 + 5 + 0))
		Assert.assertEquals(partial(new Dice(5, 13, 5)), Success(2, 2 + 4 + 0))
		Assert.assertEquals(partial(new Dice(5, 14, 5)), Success(2, 2 + 3 + 0))
		Assert.assertEquals(partial(new Dice(5, 15, 5)), Success(2, 2 + 2 + 0))
		Assert.assertEquals(partial(new Dice(5, 16, 5)), Success(2, 2 + 1 + 0))
		Assert.assertEquals(partial(new Dice(5, 17, 5)), Success(2, 2 + 0 + 0))
		Assert.assertEquals(partial(new Dice(5, 18, 5)), Success(1, 1 + 0 + 0))
		Assert.assertEquals(partial(new Dice(5, 19, 5)), Success(1, 0 + 0 + 0))
		Assert.assertEquals(partial(new Dice(5, 20, 5)), Failure(1))
		Assert.assertEquals(partial(new Dice(7, 15, 11)), Success(2, 2 + 2))
		Assert.assertEquals(partial(new Dice(7, 15, 15)), Success(2, 2 + 0))
		Assert.assertEquals(partial(new Dice(7, 15, 16)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(7, 15, 17)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(7, 15, 18)), Failure(1))
		Assert.assertEquals(partial(new Dice(17, 15, 18)), Failure(7))
	}

	@Test
	def scenario_smallPoints_smallerDifficulty {
		val partial = OutcomeCalculator.examine(defaultOptions, defaultAttributes, 4, 3)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(1, 1 + 6))
		Assert.assertEquals(partial(new Dice(11, 12, 12)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(12, 12, 12)), Success(1, 0))
		Assert.assertEquals(partial(new Dice(13, 12, 12)), Failure(1))
		Assert.assertEquals(partial(new Dice(17, 15, 18)), Failure(13))
	}

	@Test
	def scenario_smallPoints_biggerDifficulty {
		val partial = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 4, 7)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(0, 3))
		Assert.assertEquals(partial(new Dice(8, 9, 4)), Success(0, 0))
		Assert.assertEquals(partial(new Dice(8, 10, 4)), Failure(1))
		Assert.assertEquals(partial(new Dice(12, 12, 12)), Failure(9))
	}

	@Test
	def scenario_hugePoints_smallerDifficulty {
		val partial = OutcomeCalculator.examine(withoutMinimumQuality, defaultAttributes, 16, 11)_
		Assert.assertEquals(partial(new Dice(5, 5, 5)), Success(5, 5 + 6))
		Assert.assertEquals(partial(new Dice(14, 13, 12)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(14, 13, 14)), Success(0, 0))
		Assert.assertEquals(partial(new Dice(14, 14, 14)), Failure(1))
		Assert.assertEquals(partial(new Dice(14, 20, 19)), Failure(12))
	}

	@Test
	def scenario_extremeAttributes_smallerDifficulty {
		val partial = OutcomeCalculator.examine(withoutMinimumQuality, List(25, 13, 23), 9, 3)_
		Assert.assertEquals(partial(new Dice(6, 12, 16)), Success(6, 6 + 1))
		Assert.assertEquals(partial(new Dice(6, 13, 16)), Success(6, 6 + 0))
		Assert.assertEquals(partial(new Dice(6, 14, 16)), Success(5, 5))
		Assert.assertEquals(partial(new Dice(6, 18, 16)), Success(1, 1))
		Assert.assertEquals(partial(new Dice(6, 19, 16)), Success(0, 0))
		Assert.assertEquals(partial(new Dice(6, 20, 16)), Failure(1))
	}

	@Test
	def scenario_extremeAttributes_biggerDifficulty {
		val partial = OutcomeCalculator.examine(withoutMinimumQuality, List(25, 15, 23), 9, 14)_
		Assert.assertEquals(partial(new Dice(6, 4, 3)), Success(0, 6))
		Assert.assertEquals(partial(new Dice(6, 4, 16)), Success(0, 2))
		Assert.assertEquals(partial(new Dice(20, 4, 17)), Success(0, 0))
		Assert.assertEquals(partial(new Dice(6, 4, 18)), Success(0, 0))
		Assert.assertEquals(partial(new Dice(6, 4, 19)), Failure(1))
		Assert.assertEquals(partial(new Dice(6, 14, 3)), Failure(4))
	}

}
