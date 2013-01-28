package stefanholzmueller.pp2.checks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CheckStatistics {

    private double probabilityOfSuccess;
    private double averageQuality;
    private double averageQualityForSuccesses;

    public CheckStatistics(double probabilityOfSuccess, double averageQuality, double averageQualityForSuccesses) {
        this.probabilityOfSuccess = probabilityOfSuccess;
        this.averageQuality = averageQuality;
        this.averageQualityForSuccesses = averageQualityForSuccesses;
    }

    public double getProbabilityOfSuccess() {
        return probabilityOfSuccess;
    }

    public double getAverageQuality() {
        return averageQuality;
    }

    public Double getAverageQualityForSuccesses() {
        return averageQualityForSuccesses;
    }

}
