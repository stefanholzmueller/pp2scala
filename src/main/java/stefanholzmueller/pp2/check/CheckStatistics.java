package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CheckStatistics {

	private double probabilityOfSuccess;
	private double averageQualityForSuccesses;

	public CheckStatistics(double probabilityOfSuccess,
			double averageQualityForSuccesses) {
		this.probabilityOfSuccess = probabilityOfSuccess;
		this.averageQualityForSuccesses = averageQualityForSuccesses;
	}

	public double getProbabilityOfSuccess() {
		return probabilityOfSuccess;
	}

	public Double getAverageQualityForSuccesses() {
		return averageQualityForSuccesses;
	}

}
