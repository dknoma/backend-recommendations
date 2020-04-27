package com.the.mild.project.db.mongo;

import org.bson.Document;

public interface BaseDocument {

    /**
     * Get back a document
     * @return document
     */
    Document getDocument();
}
