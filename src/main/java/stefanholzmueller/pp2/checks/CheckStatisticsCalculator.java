package stefanholzmueller.pp2.checks;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("asf")
//workaround for hk2 injection... @Provider does not work
public class CheckStatisticsCalculator {

    private static final int DIE_MAX_PIPS = 20;
    private static final int NUMBER_OF_CHECKS = DIE_MAX_PIPS * DIE_MAX_PIPS * DIE_MAX_PIPS;

    private final CheckDecider checkDecider;

    @Inject
    public CheckStatisticsCalculator(CheckDecider checkDecider) {
        this.checkDecider = checkDecider;
    }

    public CheckStatistics calculate(Check check) {

        int successfulChecksTotal = 0;
        int qualityTotal = 0;

        for (int die1 = 1; die1 <= DIE_MAX_PIPS; die1++) {
            for (int die2 = 1; die2 <= DIE_MAX_PIPS; die2++) {
                for (int die3 = 1; die3 <= DIE_MAX_PIPS; die3++) {

                    IntTriple diceTriple = new IntTriple(die1, die2, die3);
                    CheckResult checkResult = checkDecider.determineResult(check, diceTriple);

                    successfulChecksTotal = incrementSuccessfulChecksTotal(checkResult, successfulChecksTotal);
                    qualityTotal = incrementQualityTotal(checkResult, qualityTotal);
                }
            }
        }

        return calculateCheckStatistics(successfulChecksTotal, qualityTotal);
    }

    private int incrementQualityTotal(CheckResult checkResult, int qualityTotal) {
        int result = qualityTotal;

        Integer quality = checkResult.getQuality();
        if (quality != null) {
            result += quality;
        }

        return result;
    }

    private int incrementSuccessfulChecksTotal(CheckResult checkResult, int successfulChecksTotal) {
        int result = successfulChecksTotal;

        if (checkResult.getOutcome().isSuccessful()) {
            result++;
        }

        return result;
    }

    private CheckStatistics calculateCheckStatistics(int successfulChecksTotal, int qualityTotal) {
        double probabilityOfSuccess = (double) successfulChecksTotal / NUMBER_OF_CHECKS;
        double averageQuality = (double) qualityTotal / NUMBER_OF_CHECKS;
        double averageQualityForSuccesses = (double) qualityTotal / successfulChecksTotal;

        return new CheckStatistics(probabilityOfSuccess, averageQuality, averageQualityForSuccesses);
    }
}
