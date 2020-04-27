package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJson {
    @JsonProperty("email") private String email;
    @JsonProperty("firstName") private String firstName;
    @JsonProperty("lastName") private String lastName;

    @JsonCreator
    public UserJson(@JsonProperty("email") String email,
                    @JsonProperty("firstName") String firstName,
                    @JsonProperty("lastName") String lastName)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return JacksonHandler.stringify(this);
    }
}
