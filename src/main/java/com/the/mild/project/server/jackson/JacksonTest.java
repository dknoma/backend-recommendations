package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class JacksonTest {
    @JsonProperty("key") private String key;
    @JsonProperty("value") private String value;

    public JacksonTest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JacksonTest setKey(String key) {
        this.key = key;
        return this;
    }

    public JacksonTest setValue(String value) {
        this.value = value;
        return this;
    }
}
