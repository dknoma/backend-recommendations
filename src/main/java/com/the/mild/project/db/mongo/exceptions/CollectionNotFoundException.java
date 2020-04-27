package com.the.mild.project.db.mongo.exceptions;

public class CollectionNotFoundException extends Exception {
    public CollectionNotFoundException(String message) {
        super(message);
    }
}
