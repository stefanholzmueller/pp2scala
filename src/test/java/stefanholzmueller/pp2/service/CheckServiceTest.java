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
import stefanholzmueller.pp2.check.OutcomeEnum;
import stefanholzmueller.pp2.check.OutcomeImpl;
import stefanholzmueller.pp2.check.CheckRoll;
import stefanholzmueller.pp2.check.OutcomeExaminer;
import stefanholzmueller.pp2.check.StatisticsGatherer;
import stefanholzmueller.pp2.util.IntTriple;

@Test
public class CheckServiceTest {

    private CheckService checkService;
    @Mock
    private OutcomeExaminer checkResultCalculator;
    @Mock
    private StatisticsGatherer checkStatisticsCalculator;

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
        OutcomeImpl checkResult = new OutcomeImpl(OutcomeEnum.LUCKY_CHECK, 7, 3);
        when(checkResultCalculator.examine(check, dice)).thenReturn(checkResult);

        OutcomeImpl serviceResult = checkService.calculateResult(checkRoll);

        assertThat(serviceResult, is(checkResult));
    }
}
