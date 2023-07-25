package com.redhat.greetings.web.api;

import com.redhat.greetings.web.domain.GreetingJSON;
import com.redhat.greetings.web.infrastructure.GreetingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloApi {

    static final Logger LOGGER = LoggerFactory.getLogger(HelloApi.class);

    @Inject
    GreetingService greetingService;

    @GET
    public Response hello() {

        GreetingJSON greeting = greetingService.randomGreeting();
        LOGGER.debug("hello: {}", greeting);
        return Response.ok().entity(greeting).build();
    }

}
