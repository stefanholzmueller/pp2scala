package stefanholzmueller.pp2.check

sealed trait Outcome

case class Success(quality: Int, gap: Int) extends Outcome
case class AutomaticSuccess(quality: Int) extends Outcome
case class SpectacularSuccess(quality: Int) extends Outcome
case class Failure(gap: Int) extends Outcome
case class AutomaticFailure extends Outcome
case class SpectacularFailure extends Outcome
case class Spruchhemmung extends Outcome

object Outcome {
	def isSuccess(outcome: Outcome) = {
		outcome match {
			case Success(_, _) => true
			case AutomaticSuccess(_) => true
			case SpectacularSuccess(_) => true
			case Failure(_) => false
			case AutomaticFailure() => false
			case SpectacularFailure() => false
			case Spruchhemmung() => false
		}
	}
}