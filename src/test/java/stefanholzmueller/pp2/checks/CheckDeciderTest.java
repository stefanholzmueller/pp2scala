package stefanholzmueller.pp2.checks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckDeciderTest {

    private static final IntTriple ALL_THREES = new IntTriple(3, 3, 3);

    private Check trivialCheck;
    private CheckDecider decider;

    @BeforeMethod
    public void setUp() throws Exception {
        trivialCheck = new Check(12, 12, 12, 4);
        decider = new CheckDecider();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfCheckIsNull() throws Exception {
        decider.determineResult(null, ALL_THREES);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfDiceTripleIsNull() throws Exception {
        decider.determineResult(trivialCheck, null);
    }

    @Test
    public void shouldReturnResult() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, ALL_THREES);

        assertThat(result, notNullValue());
    }

    @Test
    public void shouldReturnSpectacularSuccess() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(1, 1, 1));

        assertThat(result.getOutcome(), is(CheckOutcome.SPECTACULAR_SUCCESS));
    }

    @Test
    public void shouldReturnLuckySuccess() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(1, 1, 17));

        assertThat(result.getOutcome(), is(CheckOutcome.LUCKY_CHECK));
    }

    @Test
    public void shouldReturnFumble() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(20, 1, 20));

        assertThat(result.getOutcome(), is(CheckOutcome.FUMBLE));
    }

    @Test
    public void shouldReturnSpectacularFumble() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(20, 20, 20));

        assertThat(result.getOutcome(), is(CheckOutcome.SPECTACULAR_FUMBLE));
    }

    @Test
    public void shouldReturnUnsuccessful() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(18, 18, 18));

        assertThat(result.getOutcome(), is(CheckOutcome.UNSUCCESSFUL));
    }

    @Test
    public void shouldReturnSuccessful() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, ALL_THREES);

        assertThat(result.getOutcome(), is(CheckOutcome.SUCCESSFUL));
    }

    @Test
    public void shouldNotReturnFumbleForTollpatschCheck() throws Exception {
        trivialCheck.setTollpatsch(true);
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(19, 11, 18));

        assertThat(result.getOutcome(), is(not(CheckOutcome.FUMBLE)));
    }

    @Test
    public void shouldReturnFumbleForTollpatschCheck() throws Exception {
        trivialCheck.setTollpatsch(true);
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(19, 11, 20));

        assertThat(result.getOutcome(), is(CheckOutcome.FUMBLE));
    }

    @Test
    public void shouldNotReturnFumbleForFesteMatrixCheck() throws Exception {
        trivialCheck.setFesteMatrix(true);
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(20, 17, 20));

        assertThat(result.getOutcome(), is(not(CheckOutcome.FUMBLE)));
    }

    @Test
    public void shouldReturnFumbleForFesteMatrixCheck() throws Exception {
        trivialCheck.setFesteMatrix(true);
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(20, 18, 20));

        assertThat(result.getOutcome(), is(CheckOutcome.FUMBLE));
    }

    @Test
    public void shouldReturnSpruchhemmung() throws Exception {
        trivialCheck.setSpruchhemmung(true);
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(14, 14, 3));

        assertThat(result.getOutcome(), is(CheckOutcome.SPRUCHHEMMUNG));
    }

    @Test
    public void shouldReturnQuality() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, ALL_THREES);

        assertThat(result.getQuality(), is(4));
    }

    @Test
    public void shouldReturnNegativePossibleDifficulty() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(16, 16, 16));

        assertThat(result.getGap(), is(-8));
    }

    @Test
    public void shouldReturnQualityExcludingDifficulty() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, 7, 4), ALL_THREES);

        assertThat(result.getQuality(), is(3));
    }

    @Test
    public void shouldNotCountNegativeDifficultyAsQuality() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, -3, -7), ALL_THREES);

        assertThat(result.getQuality(), is(0));
    }

    @Test
    public void shouldCountZeroValueAsMinimumEffect() throws Exception {
        Check check = new Check(12, 12, 12, 0);
        check.setMinimumQuality(true);
        CheckResult result = decider.determineResult(check, ALL_THREES);

        assertThat(result.getQuality(), is(1));
    }

    @Test
    public void shouldReturnFullValueAsQuality() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, 7), new IntTriple(1, 1, 19));

        assertThat(result.getQuality(), is(7));
    }

    @Test
    public void shouldReturnMinimumEffectAsQuality() throws Exception {
        Check check = new Check(12, 12, 12, 0);
        check.setMinimumQuality(true);
        CheckResult result = decider.determineResult(check, new IntTriple(1, 1, 1));

        assertThat(result.getQuality(), is(1));
    }

    @Test
    public void shouldReturnMinimumEffectIfValueIsUsedUp() throws Exception {
        Check check = new Check(12, 12, 12, 3);
        check.setMinimumQuality(true);

        CheckResult result = decider.determineResult(check, new IntTriple(13, 13, 13));

        assertThat(result.getQuality(), is(1));
    }

    @Test
    public void shouldCountFailedPointsWithNegativeValue() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, -3), new IntTriple(10, 12, 11));

        assertThat(result.getGap(), is(-6));
    }

    @Test
    public void shouldCountFailedPointsWithPositiveValue() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(10, 12, 20));

        assertThat(result.getGap(), is(-4));
    }

    @Test
    public void shouldReturnPossibleDifficultyIfPointsArePositive() throws Exception {
        CheckResult result = decider.determineResult(trivialCheck, new IntTriple(10, 8, 6));

        assertThat(result.getGap(), is(6));
    }

    @Test
    public void shouldReturnPossibleDifficultyIfDifficultyUsesUpAllPoints() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, 3, 5), new IntTriple(9, 8, 6));

        assertThat(result.getGap(), is(1));
    }

    @Test
    public void shouldReturnPossibleDifficultyIfNegativeDifficultyGrantsPoints() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, -3, -7), new IntTriple(10, 8, 6));

        assertThat(result.getGap(), is(6));
    }

    @Test
    public void shouldReturnPossibleDifficultyIfPointsAreNegative() throws Exception {
        CheckResult result = decider.determineResult(new Check(12, 12, 12, -3), new IntTriple(1, 2, 3));

        assertThat(result.getGap(), is(6));
    }

}
