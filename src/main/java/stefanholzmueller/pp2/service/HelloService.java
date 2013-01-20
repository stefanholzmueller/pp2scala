package stefanholzmueller.pp2.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/hello")
public class HelloService {

    private RandomService randomService;

    @Inject
    public HelloService(RandomService randomService) {
        this.randomService = randomService;
    }

    @GET
    @Path("/d{pips}")
    public String sayHello(@PathParam("pips") int pips) {
        int randomDice = randomService.getRandomDice(pips);
        return "hallo " + randomDice;
    }
}
