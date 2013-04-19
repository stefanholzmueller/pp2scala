package stefanholzmueller.pp2.check

sealed trait Outcome {
	def isSuccessful: Boolean
}

sealed trait Successful extends Outcome {
	override def isSuccessful = true
}

sealed trait Unuccessful extends Outcome {
	override def isSuccessful = false
}

case class Success(quality: Int, gap: Int) extends Successful
case class AutomaticSuccess(quality: Int) extends Successful
case class SpectacularSuccess(quality: Int) extends Successful
case class Failure(gap: Int) extends Unuccessful
case class AutomaticFailure() extends Unuccessful
case class SpectacularFailure() extends Unuccessful
case class Spruchhemmung() extends Unuccessful
