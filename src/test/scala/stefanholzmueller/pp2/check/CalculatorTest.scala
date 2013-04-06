package stefanholzmueller.pp2.check

import org.testng.annotations._
import stefanholzmueller.pp2.check.Calculator.Options
import stefanholzmueller.pp2.util.Dice20
import org.testng.Assert

class CalculatorTest {

	val withFesteMatrix = new Options(false, true)
	val withWildeMagie = new Options(false, true, false)
	val withSpruchhemmung = new Options(false, false, true)
	val defaultOptions = new Options(true, false, false, false)
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
		Assert.assertEquals(outcome, Success(5, 0))
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
		val outcome = Calculator.examine(defaultOptions, defaultAttributes, 5, 0, new Dice20(19, 5, 20))
		Assert.assertEquals(outcome, Success(5, 0)) // TODO
	}

	@Test
	def wildeMagieVsSpruchhemmung {
		val outcome = Calculator.examine(new Options(false, true, true), defaultAttributes, 5, 0, new Dice20(19, 19, 3))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def wildeMagieVsSpruchhemmungVsSpectacularFailure {
		val outcome = Calculator.examine(new Options(false, true, true), defaultAttributes, 5, 0, new Dice20(19, 19, 19))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def festeMatrix {
		val outcome = Calculator.examine(withFesteMatrix, defaultAttributes, 5, 0, new Dice20(18, 20, 20))
		Assert.assertEquals(outcome, AutomaticFailure())
	}

	@Test
	def failureVsFesteMatrix {
		val outcome = Calculator.examine(withFesteMatrix, defaultAttributes, 5, 0, new Dice20(20, 17, 20))
		Assert.assertEquals(outcome, Success(5, 0)) // TODO
	}

}