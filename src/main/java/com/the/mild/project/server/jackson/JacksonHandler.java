package com.the.mild.project.server.jackson;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JacksonHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T unmarshal(String obj, Class<T> type) throws JsonProcessingException {
        return mapper.readValue(obj, type);
    }

    public static String stringify(Object jackson) {
        String result = "{}";
        try {
            result = mapper.writeValueAsString(jackson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, Object> stringToMap(String json) throws JsonProcessingException {
        final Map<String, Object> objectMap = new LinkedHashMap<>();
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);
        jsonNode.fields().forEachRemaining(e -> objectMap.put(e.getKey(), e.getValue()));

        return objectMap;
    }

    public static Document stringToDocument(String json) throws JsonProcessingException {
        final Document document = new Document();
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);
        jsonNode.fields().forEachRemaining(e -> document.put(e.getKey(), e.getValue()));

        return document;
    }

    private JacksonHandler() {
        // Utility
    }
}
