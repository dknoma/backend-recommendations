package com.the.mild.project.server.controllers;

import static com.the.mild.project.ResourceConfig.PATH_TEST_RESOURCE;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.the.mild.project.server.jackson.JacksonTest;
import com.the.mild.project.server.jackson.JacksonHandler;

/**
 * Root resource (exposed at "testresource" path)
 */
@Singleton
@Path(PATH_TEST_RESOURCE)
public class TestResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
        final JacksonTest test = new JacksonTest("test", "resource");

        return JacksonHandler.stringify(test);
    }
}
