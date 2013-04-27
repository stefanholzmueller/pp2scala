package stefanholzmueller.pp2.check;

// TODO annotations
public class OutcomeImpl implements Outcome {

	public enum OutcomeEnum {
		SPECTACULAR_SUCCESS, AUTOMATIC_SUCCESS, SUCCESS, FAILURE, AUTOMATIC_FAILURE, SPECTACULAR_FAILURE, SPRUCHHEMMUNG;
	}

	private OutcomeEnum outcomeEnum;
	private Integer quality;
	private Integer gap;

	public OutcomeImpl(OutcomeEnum outcomeEnum, Integer quality, Integer gap) {
		this.outcomeEnum = outcomeEnum;
		this.quality = quality;
		this.gap = gap;
	}

	public OutcomeEnum getOutcome() {
		return outcomeEnum;
	}

	public Integer getQuality() {
		return quality;
	}

	public Integer getGap() {
		return gap;
	}

	@Override
	public boolean isSuccessful() {
		return outcomeEnum == OutcomeEnum.SUCCESS
				|| outcomeEnum == OutcomeEnum.AUTOMATIC_SUCCESS
				|| outcomeEnum == OutcomeEnum.SPECTACULAR_SUCCESS;
	}

}
