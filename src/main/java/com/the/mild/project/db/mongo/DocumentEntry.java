package com.the.mild.project.db.mongo;

public class DocumentEntry<V> {
    private String key;
    private V value;

    public DocumentEntry(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }
}
