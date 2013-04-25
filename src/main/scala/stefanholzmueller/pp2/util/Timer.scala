package stefanholzmueller.pp2.util

import org.slf4j.LoggerFactory

class Timer(val description: String) {

	val LOGGER = LoggerFactory.getLogger(getClass)

	def info[T](f: => T) = {
		val start = System.nanoTime
		val ret = f
		val elapsed = (System.nanoTime - start) / 1e6
		LOGGER.info(s"$description took $elapsed ms. result: $ret")
		ret
	}

}

object Timer {

	def apply(description: String) = new Timer(description)

	def time[T](description: String)(f: => T) = new Timer(description).info(f)

}