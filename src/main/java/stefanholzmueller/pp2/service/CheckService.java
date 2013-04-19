package stefanholzmueller.pp2.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import stefanholzmueller.pp2.check.Check;
import stefanholzmueller.pp2.check.CheckResult;
import stefanholzmueller.pp2.check.CheckRoll;
import stefanholzmueller.pp2.check.CheckStatistics;
import stefanholzmueller.pp2.check.CheckStatisticsCalculator;
import stefanholzmueller.pp2.check.OutcomeCalculator;
import stefanholzmueller.pp2.util.IntTriple;

@Path("/check")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckService {

    private CheckStatisticsCalculator checkStatisticsCalculator;
    private OutcomeCalculator checkResultCalculator;

    @Inject
    public CheckService(CheckStatisticsCalculator checkStatisticsCalculator, OutcomeCalculator checkResultCalculator) {
        this.checkStatisticsCalculator = checkStatisticsCalculator;
        this.checkResultCalculator = checkResultCalculator;
    }

    @Path("/statistics")
    @PUT
    public CheckStatistics calculateStatistics(Check check) {
        return checkStatisticsCalculator.calculateStatistics(check);
    }

    @Path("/result")
    @PUT
    public CheckResult calculateResult(CheckRoll checkRoll) {
        Check check = checkRoll.getCheck();
        IntTriple dice = checkRoll.getDice();
        return checkResultCalculator.examine(check, dice);
    }
}
