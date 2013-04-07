package stefanholzmueller.pp2.check

import org.testng.annotations._
import stefanholzmueller.pp2.check.Calculator.Options
import stefanholzmueller.pp2.util.Dice20
import org.testng.Assert

class CalculatorTest {

	val defaultOptions = new Options(true, false, false, false)
	val withoutMinimumQuality = new Options(false, false, false, false)
	val withFesteMatrix = new Options(false, true)
	val withWildeMagie = new Options(false, true, false)
	val withSpruchhemmung = new Options(false, false, true)
	val defaultAttributes = (11, 12, 13)

	@Test
	def spectacularSuccess {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 3, -2, new Dice20(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(3))
	}

	@Test
	def automaticSuccess {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice20(1, 9, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def automaticFailure {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice20(15, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def spectacularFailure {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 5, 5, new Dice20(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def withoutSpruchhemmung {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 5, 0, new Dice20(10, 10, 11))
		Assert.assertEquals(outcome, Success(5, 1))
	}

	@Test
	def spruchhemmungVsSuccess {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(5, 8, 5))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spruchhemmungVsFailure {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(15, 18, 15))
		Assert.assertEquals(outcome, Spruchhemmung())
	}

	@Test
	def spectacularSuccessVsSpruchhemmung {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(1, 1, 1))
		Assert.assertEquals(outcome, SpectacularSuccess(5))
	}

	@Test
	def spectacularFailureVsSpruchhemmung {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(20, 20, 20))
		Assert.assertEquals(outcome, SpectacularFailure())
	}

	@Test
	def automaticFailureVsSpruchhemmung {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(20, 20, 6))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def automaticSuccessVsSpruchhemmung {
		val outcome = Calculator.examine(withSpruchhemmung, defaultAttributes, 5, 0, new Dice20(18, 1, 1))
		Assert.assertEquals(outcome, AutomaticSuccess(5))
	}

	@Test
	def wildeMagie {
		val outcome = Calculator.examine(withWildeMagie, defaultAttributes, 5, 0, new Dice20(19, 5, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsWildeMagie {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 0, 0, new Dice20(19, 5, 20))
		Assert.assertEquals(outcome, Failure(15))
	}

	@Test
	def wildeMagieVsSpruchhemmung {
		val outcome = Calculator.examine(new Options(false, true, true), defaultAttributes, 0, 0, new Dice20(19, 19, 3))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def wildeMagieVsSpruchhemmungVsSpectacularFailure {
		val outcome = Calculator.examine(new Options(false, true, true), defaultAttributes, 0, 0, new Dice20(19, 19, 19))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def festeMatrix {
		val outcome = Calculator.examine(withFesteMatrix, defaultAttributes, 0, 0, new Dice20(18, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsFesteMatrix {
		val outcome = Calculator.examine(withFesteMatrix, defaultAttributes, 0, 0, new Dice20(20, 17, 20))
		Assert.assertEquals(outcome, Failure(21))
	}

	@Test
	def success_withoutPoints_noGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice20(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_smallGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def failure_withoutPoints_negativeGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice20(15, 15, 15))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_withoutPoints_mixedGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 0, 0, new Dice20(5, 10, 15))
		Assert.assertEquals(outcome, Failure(2))
	}

	@Test
	def success_withPointsAndSameDifficulty {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 4, 4, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Success(0, 1))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_zeroQuality {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, -2, -2, new Dice20(11, 12, 13))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withoutPoints_minimumQualtity {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 0, 0, new Dice20(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withPointsAndSameDifficulty_minimumQualtity {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 4, 4, new Dice20(11, 12, 13))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def success_withNegativePointsAndSameDifficulty_minimumQualtityAndSmallGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, -2, -2, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Success(1, 1))
	}

	@Test
	def success_withNegativePoints_noGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, -2, 0, new Dice20(1, 10, 10))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_withNegativePoints_minimumQualtityAndNoGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, -2, 0, new Dice20(1, 10, 10))
		Assert.assertEquals(outcome, Success(1, 0))
	}

	@Test
	def failure_withNegativePoints_negativeGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, -2, 0, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Failure(1))
	}

	@Test
	def failure_withNegativePointsAndDifficulty_negativeGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, -2, 2, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Failure(6))
	}

	@Test
	def failure_noPointsAndDifficulty_negativeGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 0, 5, new Dice20(10, 10, 10))
		Assert.assertEquals(outcome, Failure(9))
	}

	@Test
	def failure_noPointsAndDifficulty_mixedGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 0, 4, new Dice20(7, 3, 16))
		Assert.assertEquals(outcome, Failure(7))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityAndGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 2, 9, new Dice20(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 0))
	}

	@Test
	def success_smallPointsAndHighDifficulty_noQualityButGap {
		val outcome = Calculator.examine(withoutMinimumQuality, defaultAttributes, 2, 6, new Dice20(4, 5, 6))
		Assert.assertEquals(outcome, Success(0, 3))
	}

	@Test
	def success_smallPointsAndHighDifficulty_minimumQualityAndMixedGap {
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 3, 5, new Dice20(7, 3, 6))
		Assert.assertEquals(outcome, Success(1, 2))
	}

}