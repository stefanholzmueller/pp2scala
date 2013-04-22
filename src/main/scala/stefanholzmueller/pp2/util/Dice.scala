package stefanholzmueller.pp2.util

class Dice(values: Int*) {
	val elements = Vector(values: _*)
	for (x <- elements) {
		require(x >= 1 && x <= 20, "dice values must be between 1 and 20")
	}

	def apply(i: Int) = elements(i)

	def sum = elements.sum

	def allEqualTo(n: Int) = elements.forall(_ == n)

	def twoEqualTo(n: Int) = twoWithFilter(_ == n)

	def twoGreaterThan(n: Int) = twoWithFilter(_ > n)

	private def twoWithFilter(f: Int => Boolean) = elements.filter(f).length >= 2

	def twoSameValues() = {
		elements.groupBy(identity).values.exists(_.length >= 2)
	}

	def compareWithAttributes(attributeList: List[Int]) = {
		elements.zip(attributeList).map(Function.tupled((die, attr) => die - attr))
	}

	override def toString = elements.mkString(", ")

}