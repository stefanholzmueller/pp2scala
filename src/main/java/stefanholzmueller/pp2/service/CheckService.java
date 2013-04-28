package stefanholzmueller.pp2.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import stefanholzmueller.pp2.check.Check;
import stefanholzmueller.pp2.check.CheckRoll;
import stefanholzmueller.pp2.check.OutcomeCalculatorAdapter;
import stefanholzmueller.pp2.check.OutcomeExaminer;
import stefanholzmueller.pp2.check.OutcomeImpl;
import stefanholzmueller.pp2.check.SimpleStatisticsCalculatorAdapter;
import stefanholzmueller.pp2.check.Statistics;
import stefanholzmueller.pp2.check.StatisticsGatherer;
import stefanholzmueller.pp2.util.IntTriple;

@Path("/check")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckService {

	private StatisticsGatherer statisticsGatherer = new SimpleStatisticsCalculatorAdapter();
	private OutcomeExaminer outcomeExaminer = new OutcomeCalculatorAdapter();

	public CheckService() {
	}

	// @Inject
	public CheckService(StatisticsGatherer statisticsGatherer,
			OutcomeExaminer outcomeExaminer) {
		this.statisticsGatherer = statisticsGatherer;
		this.outcomeExaminer = outcomeExaminer;
	}

	@Path("/statistics")
	@PUT
	public Statistics gatherStatistics(Check check) {
		return statisticsGatherer.gather(check);
	}

	@Path("/outcome")
	@PUT
	public OutcomeImpl examineOutcome(CheckRoll checkRoll) {
		Check check = checkRoll.getCheck();
		IntTriple dice = checkRoll.getDice();
		return outcomeExaminer.examine(check, dice);
	}
}
