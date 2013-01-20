package stefanholzmueller.pp2.checks;

public class CheckResult {

    private CheckOutcome checkOutcome;
    private Integer quality;
    private Integer gap;

    public CheckResult(CheckOutcome checkOutcome, Integer quality, Integer gap) {
        this.checkOutcome = checkOutcome;
        this.quality = quality;
        this.gap = gap;
    }

    public CheckOutcome getOutcome() {
        return checkOutcome;
    }

    public Integer getQuality() {
        return quality;
    }

    public Integer getGap() {
        return gap;
    }

}
