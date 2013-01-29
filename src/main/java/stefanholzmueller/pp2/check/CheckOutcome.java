package stefanholzmueller.pp2.check;

public enum CheckOutcome {
    SUCCESSFUL, UNSUCCESSFUL, SPECTACULAR_SUCCESS, LUCKY_CHECK, SPECTACULAR_FUMBLE, FUMBLE, SPRUCHHEMMUNG;

    boolean isSuccessful() {
        return this == SUCCESSFUL || this == SPECTACULAR_SUCCESS || this == LUCKY_CHECK;
    }

    boolean isFumble() {
        return this == FUMBLE || this == SPECTACULAR_FUMBLE;
    }
}
