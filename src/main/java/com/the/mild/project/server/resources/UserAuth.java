package com.the.mild.project.server.resources;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class UserAuth {

    private static final String clientId = "GOOGLE_CLIENT_ID";

    /**
     * Checks user auth with Google servers and returns the payload with user data.
     * For testing, idTokenstring can start with test and the payload will be filled with dummy data.
     *
     * @param idTokenString Google Token ID sent from frontend.
     * @return Payload object with user data.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static Payload checkAuth(String idTokenString) throws IOException, GeneralSecurityException {

        String CLIENT_ID = "GOOGLE_CLIENT_ID";
        String clientId = System.getenv(CLIENT_ID);

        if (idTokenString.startsWith("test")) {
            int identifier = Integer.parseInt(idTokenString.split("-")[1]);
            Payload payload = new Payload();
            payload.setSubject(idTokenString);
            payload.setEmail(String.format("fake_%d@fake.com", identifier));
            payload.set("given_name", "Fake");
            payload.set("family_name", "Faker");
            payload.set("exp", (long) 1682847246);
            return payload;
        }

        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken;

        try {
            idToken = verifier.verify(idTokenString);
            Payload payload = idToken.getPayload();
            return payload;
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }
}
