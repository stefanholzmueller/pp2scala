package stefanholzmueller.pp2.check;

// TODO annotations
public class OutcomeImpl implements Outcome {

	private OutcomeEnum checkOutcome;
	private Integer quality;
	private Integer gap;

	public OutcomeImpl(OutcomeEnum checkOutcome, Integer quality, Integer gap) {
		this.checkOutcome = checkOutcome;
		this.quality = quality;
		this.gap = gap;
	}

	public OutcomeEnum getOutcome() {
		return checkOutcome;
	}

	public Integer getQuality() {
		return quality;
	}

	public Integer getGap() {
		return gap;
	}

	@Override
	public boolean isSuccessful() {
		return checkOutcome.isSuccessful();
	}

}
