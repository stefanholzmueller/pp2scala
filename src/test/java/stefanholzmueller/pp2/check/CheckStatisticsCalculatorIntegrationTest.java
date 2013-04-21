package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckStatisticsCalculatorIntegrationTest {

	private StatisticsGatherer realCalculator;
	private Check trivialCheck;

	@BeforeMethod
	public void setUp() throws Exception {
		realCalculator = new CheckStatisticsCalculator(
				new CheckResultCalculator());
		trivialCheck = new Check(12, 12, 12, 4);
	}

	@Test
	public void shouldCalculateProbabilityOfSuccess() throws Exception {
		CheckStatistics checkStatistics = realCalculator.gather(trivialCheck);

		assertThat(checkStatistics.getChance(), is(0.461));
	}

	@Test
	public void shouldCalculateAverageQualityForSuccesses() throws Exception {
		CheckStatistics checkStatistics = realCalculator.gather(trivialCheck);

		assertThat(checkStatistics.getAverageQuality(), is(2.6374728850325377));
	}

}
