package stefanholzmueller.pp2.check

sealed trait Successful extends Outcome {
	override def isSuccessful = true
}

sealed trait Unuccessful extends Outcome {
	override def isSuccessful = false
}

case class Success(quality: Int, rest: Int) extends Successful {
	override def getMessage = s"Gelungen mit $quality Punkten (maximale Erschwernis $rest)"
}
case class AutomaticSuccess(quality: Int) extends Successful {
	override def getMessage = s"Automatischer Erfolg! Alle $quality Punkte übrig"
}
case class SpectacularSuccess(quality: Int) extends Successful {
	override def getMessage = s"Spektakulärer Erfolg! Alle $quality Punkte übrig"
}
case class Failure(gap: Int) extends Unuccessful {
	override def getMessage = s"Misslungen (um $gap daneben)"
}
case class AutomaticFailure() extends Unuccessful {
	override def getMessage = "Patzer!"
}
case class SpectacularFailure() extends Unuccessful {
	override def getMessage = "Spektakulärer Patzer!"
}
case class Spruchhemmung() extends Unuccessful {
	override def getMessage = "Spruchhemmung!"
}
