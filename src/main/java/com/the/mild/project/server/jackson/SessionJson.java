package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionJson {
    @JsonProperty("googleId") private String googleId;
    @JsonProperty("email") private String email;
    @JsonProperty("expirationTime") private int expirationTime;

    @JsonCreator
    public SessionJson(@JsonProperty("googleId") String googleId,
                    @JsonProperty("email") String email,
                    @JsonProperty("expirationTime") int expirationTime)
    {
        this.googleId = googleId;
        this.email = email;
        this.expirationTime = expirationTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGoogleId(String id) {
        this.googleId = id;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getEmail() {
        return email;
    }

    public String getGoogleId() {
        return googleId;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    @Override
    public String toString() {
        return JacksonHandler.stringify(this);
    }
}
