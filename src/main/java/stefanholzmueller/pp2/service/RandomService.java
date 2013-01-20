package stefanholzmueller.pp2.service;

import java.security.SecureRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/random")
public class RandomService {

    private SecureRandom random = new SecureRandom();

    @GET
    @Path("/d{pips}")
    public int getRandomDice(@PathParam("pips") int pips) {
        return random.nextInt(pips) + 1;
    }
}
