package com.the.mild.project.server.controllers;

import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_EXAMPLE_ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;
import static com.the.mild.project.ResourceConfig.PATH_TEST_RESOURCE_WITH_MULTIPLE_PARAMS;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.the.mild.project.server.jackson.MultipleParamsTest;
import com.the.mild.project.server.jackson.JacksonHandler;

@Singleton
@Path(PATH_TEST_RESOURCE_WITH_MULTIPLE_PARAMS)
public class TestResourceWithMultipleParams {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIds(@PathParam(PATH_PARAM_ID) String id, @PathParam(PATH_PARAM_EXAMPLE_ID) String exampleId) {
        final MultipleParamsTest test = new MultipleParamsTest(id, exampleId);

        return JacksonHandler.stringify(test);
    }
}
