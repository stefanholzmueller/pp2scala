package stefanholzmueller.pp2.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import stefanholzmueller.pp2.check.Check;
import stefanholzmueller.pp2.check.CheckOutcome;
import stefanholzmueller.pp2.check.CheckResult;
import stefanholzmueller.pp2.check.CheckResultCalculator;
import stefanholzmueller.pp2.check.CheckRoll;
import stefanholzmueller.pp2.check.CheckStatisticsCalculator;
import stefanholzmueller.pp2.util.IntTriple;

@Test
public class CheckServiceTest {

    private CheckService checkService;
    @Mock
    private CheckResultCalculator checkResultCalculator;
    @Mock
    private CheckStatisticsCalculator checkStatisticsCalculator;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        checkService = new CheckService(checkStatisticsCalculator, checkResultCalculator);
    }

    public void delegatesToCheckResultCalculator() {
        CheckRoll checkRoll = mock(CheckRoll.class);
        Check check = new Check(12, 13, 14, 5);
        IntTriple dice = new IntTriple(1, 16, 1);
        when(checkRoll.getCheck()).thenReturn(check);
        when(checkRoll.getDice()).thenReturn(dice);
        CheckResult checkResult = new CheckResult(CheckOutcome.LUCKY_CHECK, 7, 3);
        when(checkResultCalculator.calculateResult(check, dice)).thenReturn(checkResult);

        CheckResult serviceResult = checkService.calculateResult(checkRoll);

        assertThat(serviceResult, is(checkResult));
    }
}
