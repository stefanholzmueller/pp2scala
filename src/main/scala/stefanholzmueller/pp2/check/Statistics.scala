package stefanholzmueller.pp2.check

case class Statistics(val chance: Double, val average: Double) {
	override def toString = {
		val percentage = chance * 100
		f"$percentage%2.2f % chance for $average%.2f points"
	}
}