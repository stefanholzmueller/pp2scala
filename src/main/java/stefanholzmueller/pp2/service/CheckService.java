package stefanholzmueller.pp2.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import stefanholzmueller.pp2.checks.Check;
import stefanholzmueller.pp2.checks.CheckStatistics;
import stefanholzmueller.pp2.checks.CheckStatisticsCalculator;

@Path("/check")
public class CheckService {

    private CheckStatisticsCalculator checkStatisticsCalculator;

    @Inject
    public CheckService(CheckStatisticsCalculator checkStatisticsCalculator) {
        this.checkStatisticsCalculator = checkStatisticsCalculator;
    }

    @Path("/statistics")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CheckStatistics calculate(Check check) {
        return checkStatisticsCalculator.calculate(check);
    }
}
