package stefanholzmueller.pp2.check

class Options(
	val minimumQuality: Boolean,
	val festeMatrix: Boolean,
	val wildeMagie: Boolean,
	val spruchhemmung: Boolean) {

	def this(mq: Boolean) = this(mq, false, false, false)
	def this(mq: Boolean, fm: Boolean) = this(mq, fm, false, false)
	def this(mq: Boolean, wm: Boolean, sh: Boolean) = this(mq, false, wm, sh)

	require(!(festeMatrix && (wildeMagie || spruchhemmung)))
}
