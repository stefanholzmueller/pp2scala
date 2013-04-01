package stefanholzmueller.pp2.check

sealed trait Outcome

case class Success(quality: Int, rest: Int) extends Outcome
case class AutomaticSuccess(quality: Int, rest: Int) extends Outcome
case class SpectacularSuccess(quality: Int, rest: Int) extends Outcome
case class Failure(gap: Int) extends Outcome
case class AutomaticFailure(gap: Int) extends Outcome
case class SpectacularFailure(gap: Int) extends Outcome
case class Spruchhemmung extends Outcome

object Outcome {
	def isSuccess(outcome: Outcome) = {
		outcome match {
			case Success(_, _) => true
			case AutomaticSuccess(_, _) => true
			case SpectacularSuccess(_, _) => true
			case Failure(_) => false
			case AutomaticFailure(_) => false
			case SpectacularFailure(_) => false
			case Spruchhemmung() => false
		}
	}
}