package stefanholzmueller.pp2.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import stefanholzmueller.pp2.check.Check;
import stefanholzmueller.pp2.check.CheckResult;
import stefanholzmueller.pp2.check.CheckRoll;
import stefanholzmueller.pp2.check.OutcomeExaminer;
import stefanholzmueller.pp2.check.SimpleStatisticsCalculatorAdapter;
import stefanholzmueller.pp2.check.StatisticsGatherer;
import stefanholzmueller.pp2.check.StatisticsGatherer.CheckStatistics;
import stefanholzmueller.pp2.util.IntTriple;

@Path("/check")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckService {

	private StatisticsGatherer statisticsGatherer = new SimpleStatisticsCalculatorAdapter();
	private OutcomeExaminer checkResultCalculator;

	public CheckService() {
	}

	// @Inject
	public CheckService(StatisticsGatherer checkStatisticsCalculator,
			OutcomeExaminer checkResultCalculator) {
		this.statisticsGatherer = checkStatisticsCalculator;
		this.checkResultCalculator = checkResultCalculator;
	}

	@Path("/statistics")
	@PUT
	public CheckStatistics calculateStatistics(Check check) {
		return statisticsGatherer.gather(check);
	}

	@Path("/result")
	@PUT
	public CheckResult calculateResult(CheckRoll checkRoll) {
		Check check = checkRoll.getCheck();
		IntTriple dice = checkRoll.getDice();
		return checkResultCalculator.examine(check, dice);
	}
}
