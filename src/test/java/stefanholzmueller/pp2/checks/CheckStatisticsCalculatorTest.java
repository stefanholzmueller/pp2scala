package stefanholzmueller.pp2.checks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckStatisticsCalculatorTest {

    private CheckStatisticsCalculator calculator;
    private Check trivialCheck;

    @Mock
    private CheckDecider checkDecider;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        calculator = new CheckStatisticsCalculatorImpl(checkDecider);
        trivialCheck = new Check(12, 12, 12, 4);
    }

    @Test
    public void shouldRunCheckDecider8000Times() throws Exception {
        allChecksAreSuccessful();

        calculator.calculate(trivialCheck);

        verify(checkDecider, times(8000)).determineResult(eq(trivialCheck), any(IntTriple.class));
    }

    @Test
    public void shouldReturnMaximumProbabilityIfEveryCheckIsSuccessful() throws Exception {
        allChecksAreSuccessful();

        CheckStatistics checkStatistics = calculator.calculate(trivialCheck);

        assertThat(checkStatistics.getProbabilityOfSuccess(), is(1.0));
    }

    @Test
    public void shouldReturnAverageQualityIfEveryCheckIsSuccessful() throws Exception {
        allChecksAreSuccessful();

        CheckStatistics checkStatistics = calculator.calculate(trivialCheck);

        assertThat(checkStatistics.getAverageQuality(), is(4.0));
    }

    @Test
    public void shouldReturnAverageQualityForSuccesses() throws Exception {
        CheckResult successful = new CheckResult(CheckOutcome.SUCCESSFUL, 3, null);
        CheckResult unsuccessful = new CheckResult(CheckOutcome.UNSUCCESSFUL, null, null);
        when(checkDecider.determineResult(eq(trivialCheck), any(IntTriple.class))).thenReturn(successful, unsuccessful);

        CheckStatistics checkStatistics = calculator.calculate(trivialCheck);

        assertThat(checkStatistics.getAverageQualityForSuccesses(), is(3.0));
    }

    private void allChecksAreSuccessful() {
        CheckResult checkResult = new CheckResult(CheckOutcome.SUCCESSFUL, 4, null);
        when(checkDecider.determineResult(eq(trivialCheck), any(IntTriple.class))).thenReturn(checkResult);
    }
}
