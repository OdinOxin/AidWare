package de.odinoxin.aidware.aidcloud;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("Hello")
public class Hello {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMsg() {
        return "Here I am!";
    }
}
