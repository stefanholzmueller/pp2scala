package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import stefanholzmueller.pp2.check.Check;
import stefanholzmueller.pp2.check.CheckResultCalculator;
import stefanholzmueller.pp2.check.CheckStatistics;
import stefanholzmueller.pp2.check.CheckStatisticsCalculator;


public class CheckStatisticsCalculatorIntegrationTest {

    private CheckStatisticsCalculator realCalculator;
    private Check trivialCheck;

    @BeforeMethod
    public void setUp() throws Exception {
        realCalculator = new CheckStatisticsCalculator(new CheckResultCalculator());
        trivialCheck = new Check(12, 12, 12, 4);
    }

    @Test
    public void shouldCalculateProbabilityOfSuccess() throws Exception {
        CheckStatistics checkStatistics = realCalculator.calculateStatistics(trivialCheck);

        assertThat(checkStatistics.getProbabilityOfSuccess(), is(0.461));
    }

    @Test
    public void shouldCalculateAverageQuality() throws Exception {
        CheckStatistics checkStatistics = realCalculator.calculateStatistics(trivialCheck);

        assertThat(checkStatistics.getAverageQuality(), is(1.215875));
    }

    @Test
    public void shouldCalculateAverageQualityForSuccesses() throws Exception {
        CheckStatistics checkStatistics = realCalculator.calculateStatistics(trivialCheck);

        assertThat(checkStatistics.getAverageQualityForSuccesses(), is(2.6374728850325377));
    }

}
