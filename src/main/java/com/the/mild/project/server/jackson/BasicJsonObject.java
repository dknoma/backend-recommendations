package com.the.mild.project.server.jackson;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BasicJsonObject implements Map<String, Object> {
    private final Map<String, Object> objectMap = new LinkedHashMap<>();

    public void fromString(String json) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode jsonNode = mapper.readValue(json, ObjectNode.class);
        jsonNode.fields().forEachRemaining(e -> this.objectMap.put(e.getKey(), e.getValue()));
    }

    public String toJsonString() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode objectNode = mapper.createObjectNode();

        objectMap.forEach(objectNode::putPOJO);
        return mapper.writeValueAsString(objectNode);
    }

    public String toPrettyJsonString() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode objectNode = mapper.createObjectNode();

        objectMap.forEach(objectNode::putPOJO);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    }

    @Override
    public int size() {
        return this.objectMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.objectMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.objectMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.objectMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.objectMap.get(key);
    }

    @Override
    public Object put(String key, Object obj) {
        return this.objectMap.put(key, obj);
    }

    @Override
    public Object remove(Object key) {
        return this.objectMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.objectMap.putAll(m);
    }

    @Override
    public void clear() {
        this.objectMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.objectMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.objectMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.objectMap.entrySet();
    }


}
