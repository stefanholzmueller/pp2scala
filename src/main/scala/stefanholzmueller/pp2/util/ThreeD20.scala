package stefanholzmueller.pp2.util

class ThreeD20(a: Int, b: Int, c: Int) { // TODO Dice20(varargs)
	val elements = Vector(a, b, c)
	for (x <- elements) {
		require(x >= 1 && x <= 20, "dice values must be between 1 and 20")
	}

	def apply(i: Int) = elements(i)

	def allEqualTo(n: Int) = elements.forall(_ == n)

	def twoEqualTo(n: Int) = elements.filter(_ == n).length >= 2

	def twoSameValues() = {
		val groupedLists = elements.groupBy(identity).values
		val everyValueJustOnce = groupedLists.forall(_.length == 1)
		!everyValueJustOnce
	}
}