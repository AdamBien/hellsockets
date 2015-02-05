package com.airhacks.hellsockets.overload.boundary;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DeploymentException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Path("loader")
@Stateless
public class LoaderResource {

    @Inject
    LoadManager loadManager;

    @POST
    public Response define(JsonObject configuration) {
        String uri = configuration.getString("uri");
        String message = configuration.getString("message");
        int sessions = configuration.getInt("sessions");
        int iterations = configuration.getInt("iterations");

        try {
            loadManager.start(uri, message, sessions, iterations);
        } catch (DeploymentException | IOException | URISyntaxException | InterruptedException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("x-reason", ex.toString()).build();
        }
        return Response.accepted().build();
    }

    @OPTIONS
    public JsonObject sample() {
        return Json.createObjectBuilder().
                add("uri", "ws://localhost:8080/websockets/messages").
                add("message", "hey duke").
                add("sessions", 10).
                add("iterations", 10).build();
    }

}
