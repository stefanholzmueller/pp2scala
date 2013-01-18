package stfnhlzmllr.service;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/random")
public class RandomService {

    @GET
    @Path("/d{pips}")
    public int getRandomDice(@PathParam("pips") int pips) {
        int result = new Random().nextInt(pips) + 1;
        return result;
    }
}
