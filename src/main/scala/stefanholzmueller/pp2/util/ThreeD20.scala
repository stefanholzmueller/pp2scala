package stefanholzmueller.pp2.util

class ThreeD20(a: Int, b: Int, c: Int) {
  val elements = Vector(a, b, c)

  def apply(i: Int) = elements(i)

  def allEqualTo(n: Int) = elements.forall(_ == n)

  def twoEqualTo(n: Int) = elements.filter(_ == n).length >= 2

  def twoSameValues() = elements(0) == elements(1) || elements(0) == elements(2) || elements(1) == elements(2)
}