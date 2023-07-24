package com.redhat.greetings.web.api;

import com.redhat.greetings.web.domain.GreetingJSON;
import com.redhat.greetings.web.domain.GreetingSubmission;
import com.redhat.greetings.web.infrastructure.GreetingService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/greeting")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RESTApi {

    static final Logger LOGGER = LoggerFactory.getLogger(RESTApi.class);

    @Inject
    GreetingService greetingService;

    @POST
    @Transactional
    public Response addGreeting(GreetingJSON greetingJSON) {

        LOGGER.debug("adding Greeting: {}", greetingJSON);
        greetingService.processGreetingSubmission(greetingJSON);
        return Response.accepted().build();
    }

    @GET
    @Path("/all")
    public Response allGreetings(){

        return Response.ok().entity(greetingService.listAllGreetings()).build();
    }

    @GET
    public Response hello() {

        return Response.ok().entity(greetingService.randomGreeting()).build();
    }

    @GET
    @Path("/submissions")
    public Response allSubmissions() {

        List<GreetingSubmission> all = GreetingSubmission.listAll();
        return Response.ok().entity(all).build();
    }

}
