package stefanholzmueller.pp2.check

sealed trait Outcome

case class Success(quality: Int, rest: Int) extends Outcome
case class AutomaticSuccess(quality: Int, rest: Int) extends Outcome
case class SpectacularSuccess(quality: Int, rest: Int) extends Outcome
case class Failure(gap: Int) extends Outcome
case class AutomaticFailure(gap: Int) extends Outcome
case class SpectacularFailure(gap: Int) extends Outcome
case class Spruchhemmung extends Outcome
