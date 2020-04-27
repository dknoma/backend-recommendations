package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MultipleParamsTest {
    @JsonProperty("id") private String id;
    @JsonProperty("exampleId") private String exampleId;

    public MultipleParamsTest(String id, String exampleId) {
        this.id = id;
        this.exampleId = exampleId;
    }

    public MultipleParamsTest setId(String id) {
        this.id = id;
        return this;
    }

    public MultipleParamsTest setExampleId(String exampleId) {
        this.exampleId = exampleId;
        return this;
    }
}
