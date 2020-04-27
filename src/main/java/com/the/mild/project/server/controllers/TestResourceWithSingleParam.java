package com.the.mild.project.server.controllers;

import static com.the.mild.project.ResourceConfig.PATH_TEST_RESOURCE_WITH_PARAM;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.the.mild.project.server.jackson.ParamTest;
import com.the.mild.project.server.jackson.JacksonHandler;

@Singleton
@Path(PATH_TEST_RESOURCE_WITH_PARAM)
public class TestResourceWithSingleParam {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getId(@PathParam(PATH_PARAM_ID) String id) {
        final ParamTest test = new ParamTest(id);

        return JacksonHandler.stringify(test);
    }
}
