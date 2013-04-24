package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Statistics {

	private double chance;
	private double averageQuality;

	public Statistics(double chance, double averageQuality) {
		this.chance = chance;
		this.averageQuality = averageQuality;
	}

	public double getChance() {
		return chance;
	}

	public double getAverageQuality() {
		return averageQuality;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(averageQuality);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(chance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistics other = (Statistics) obj;
		if (Double.doubleToLongBits(averageQuality) != Double
				.doubleToLongBits(other.averageQuality))
			return false;
		if (Double.doubleToLongBits(chance) != Double
				.doubleToLongBits(other.chance))
			return false;
		return true;
	}

}
