package stefanholzmueller.pp2.check

import org.testng.annotations._
import org.hamcrest.MatcherAssert._
import org.hamcrest.Matchers._

class OutcomeTest {

	@Test
	def patternMatching {
		val outcome = Success(3, 2): Outcome
		val resultOfMatch = outcome match {
			case Success(q, r) => q == 3 && r == 2
			case AutomaticSuccess(_) => false
			case SpectacularSuccess(_) => false
			case Failure(_) => false
			case AutomaticFailure() => false
			case SpectacularFailure() => false
			case Spruchhemmung() => false
		}

		assertThat(resultOfMatch, is(true))
	}

	@Test
	def isSuccess {
		assertThat(Outcome.isSuccess(AutomaticSuccess(3)), is(true))
		assertThat(Outcome.isSuccess(Failure(7)), is(false))
	}

}