package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TodoJson {
    @JsonProperty("username") private String username;
    @JsonProperty("message") private String message;
    @JsonProperty("completed") private boolean completed;

    public TodoJson() {
    }

    @JsonCreator
    public TodoJson(@JsonProperty("username") String username,
                    @JsonProperty("message") String message,
                    @JsonProperty("completed") boolean completed) {
        this.username = username;
        this.message = message;
        this.completed = completed;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return JacksonHandler.stringify(this);
    }

    @Override
    public boolean equals(Object obj) {
        assert obj.getClass().equals(TodoJson.class);

        TodoJson other = (TodoJson) obj;
        return this.username.equals(other.username) && this.message.equals(other.message) && this.completed == other.completed;
    }
}
