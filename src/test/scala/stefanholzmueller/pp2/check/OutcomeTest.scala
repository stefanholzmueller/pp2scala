package stefanholzmueller.pp2.check

import org.testng.annotations._
import org.hamcrest.MatcherAssert._
import org.hamcrest.Matchers._

class OutcomeTest {

	@Test
	def patternMatching {
		val outcome = Success(3, 2): Outcome
		val resultOfMatch = outcome match {
			case Success(q, r) => true
			case AutomaticSuccess(q, r) => false
			case SpectacularSuccess(q, r) => false
			case Failure(g) => false
			case AutomaticFailure(g) => false
			case SpectacularFailure(g) => false
			case Spruchhemmung() => false
		}

		assertThat(resultOfMatch, is(true))
	}
}