package stefanholzmueller.pp2.check;

public enum OutcomeEnum implements Outcome {
	SUCCESSFUL, UNSUCCESSFUL, SPECTACULAR_SUCCESS, LUCKY_CHECK, SPECTACULAR_FUMBLE, FUMBLE, SPRUCHHEMMUNG;

	public boolean isSuccessful() {
		return this == SUCCESSFUL || this == SPECTACULAR_SUCCESS
				|| this == LUCKY_CHECK;
	}

}
