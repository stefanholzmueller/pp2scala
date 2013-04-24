package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import stefanholzmueller.pp2.util.IntTriple;

public class CheckResultCalculatorTest {

	private static final IntTriple ALL_THREES = new IntTriple(3, 3, 3);

	private Check trivialCheck;
	private OutcomeExaminer calculator;

	@BeforeMethod
	public void setUp() throws Exception {
		trivialCheck = new Check(12, 12, 12, 4);
		calculator = new CheckResultCalculator();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldThrowExceptionIfCheckIsNull() throws Exception {
		calculator.examine(null, ALL_THREES);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldThrowExceptionIfDiceTripleIsNull() throws Exception {
		calculator.examine(trivialCheck, null);
	}

	@Test
	public void shouldReturnResult() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				ALL_THREES);

		assertThat(result, notNullValue());
	}

	@Test
	public void shouldReturnSpectacularSuccess() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(1, 1, 1));

		assertThat(result.getOutcome(), is(OutcomeEnum.SPECTACULAR_SUCCESS));
	}

	@Test
	public void shouldReturnLuckySuccess() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(1, 1, 17));

		assertThat(result.getOutcome(), is(OutcomeEnum.LUCKY_CHECK));
	}

	@Test
	public void shouldReturnFumble() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(20, 1, 20));

		assertThat(result.getOutcome(), is(OutcomeEnum.FUMBLE));
	}

	@Test
	public void shouldReturnSpectacularFumble() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(20, 20, 20));

		assertThat(result.getOutcome(), is(OutcomeEnum.SPECTACULAR_FUMBLE));
	}

	@Test
	public void shouldReturnUnsuccessful() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(18, 18, 18));

		assertThat(result.getOutcome(), is(OutcomeEnum.UNSUCCESSFUL));
	}

	@Test
	public void shouldReturnSuccessful() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				ALL_THREES);

		assertThat(result.getOutcome(), is(OutcomeEnum.SUCCESSFUL));
	}

	@Test
	public void shouldNotReturnFumbleForTollpatschCheck() throws Exception {
		trivialCheck.setTollpatsch(true);
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(19, 11, 18));

		assertThat(result.getOutcome(), is(not(OutcomeEnum.FUMBLE)));
	}

	@Test
	public void shouldReturnFumbleForTollpatschCheck() throws Exception {
		trivialCheck.setTollpatsch(true);
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(19, 11, 20));

		assertThat(result.getOutcome(), is(OutcomeEnum.FUMBLE));
	}

	@Test
	public void shouldNotReturnFumbleForFesteMatrixCheck() throws Exception {
		trivialCheck.setFesteMatrix(true);
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(20, 17, 20));

		assertThat(result.getOutcome(), is(not(OutcomeEnum.FUMBLE)));
	}

	@Test
	public void shouldReturnFumbleForFesteMatrixCheck() throws Exception {
		trivialCheck.setFesteMatrix(true);
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(20, 18, 20));

		assertThat(result.getOutcome(), is(OutcomeEnum.FUMBLE));
	}

	@Test
	public void shouldReturnSpruchhemmung() throws Exception {
		trivialCheck.setSpruchhemmung(true);
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(14, 14, 3));

		assertThat(result.getOutcome(), is(OutcomeEnum.SPRUCHHEMMUNG));
	}

	@Test
	public void shouldReturnQuality() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				ALL_THREES);

		assertThat(result.getQuality(), is(4));
	}

	@Test
	public void shouldReturnNegativePossibleDifficulty() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(16, 16, 16));

		assertThat(result.getGap(), is(-8));
	}

	@Test
	public void shouldReturnQualityExcludingDifficulty() throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				7, 4), ALL_THREES);

		assertThat(result.getQuality(), is(3));
	}

	@Test
	public void shouldNotCountNegativeDifficultyAsQuality() throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				-3, -7), ALL_THREES);

		assertThat(result.getQuality(), is(0));
	}

	@Test
	public void shouldCountZeroValueAsMinimumEffect() throws Exception {
		Check check = new Check(12, 12, 12, 0);
		check.setMinimumQuality(true);
		OutcomeImpl result = calculator.examine(check, ALL_THREES);

		assertThat(result.getQuality(), is(1));
	}

	@Test
	public void shouldReturnFullValueAsQuality() throws Exception {
		OutcomeImpl result = calculator.examine(
				new Check(12, 12, 12, 7), new IntTriple(1, 1, 19));

		assertThat(result.getQuality(), is(7));
	}

	@Test
	public void shouldReturnMinimumEffectAsQuality() throws Exception {
		Check check = new Check(12, 12, 12, 0);
		check.setMinimumQuality(true);
		OutcomeImpl result = calculator.examine(check, new IntTriple(1,
				1, 1));

		assertThat(result.getQuality(), is(1));
	}

	@Test
	public void shouldReturnMinimumEffectIfValueIsUsedUp() throws Exception {
		Check check = new Check(12, 12, 12, 3);
		check.setMinimumQuality(true);

		OutcomeImpl result = calculator.examine(check, new IntTriple(
				13, 13, 13));

		assertThat(result.getQuality(), is(1));
	}

	@Test
	public void shouldCountFailedPointsWithNegativeValue() throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				-3), new IntTriple(10, 12, 11));

		assertThat(result.getGap(), is(-6));
	}

	@Test
	public void shouldCountFailedPointsWithPositiveValue() throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(10, 12, 20));

		assertThat(result.getGap(), is(-4));
	}

	@Test
	public void shouldReturnPossibleDifficultyIfPointsArePositive()
			throws Exception {
		OutcomeImpl result = calculator.examine(trivialCheck,
				new IntTriple(10, 8, 6));

		assertThat(result.getGap(), is(6));
	}

	@Test
	public void shouldReturnPossibleDifficultyIfDifficultyUsesUpAllPoints()
			throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				3, 5), new IntTriple(9, 8, 6));

		assertThat(result.getGap(), is(1));
	}

	@Test
	public void shouldReturnPossibleDifficultyIfNegativeDifficultyGrantsPoints()
			throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				-3, -7), new IntTriple(10, 8, 6));

		assertThat(result.getGap(), is(6));
	}

	@Test
	public void shouldReturnPossibleDifficultyIfPointsAreNegative()
			throws Exception {
		OutcomeImpl result = calculator.examine(new Check(12, 12, 12,
				-3), new IntTriple(1, 2, 3));

		assertThat(result.getGap(), is(6));
	}

}
