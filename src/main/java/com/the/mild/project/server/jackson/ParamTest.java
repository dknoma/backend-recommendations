package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ParamTest {
    @JsonProperty("id") private String id;

    public ParamTest(String id) {
        this.id = id;
    }

    public ParamTest setId(String id) {
        this.id = id;
        return this;
    }
}
