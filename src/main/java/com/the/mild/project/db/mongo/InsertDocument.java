package com.the.mild.project.db.mongo;

import org.bson.Document;

public abstract class InsertDocument implements BaseDocument {
    protected Document document;

    protected InsertDocument() {
        this.document = new Document();
    }

    /**
     * Put data into a document.
     * @param documentEntries;
     * @param data;
     * @param <E>;
     */
    protected <E extends InsertDocumentEntry> void putData(Class<E> documentEntries, Object... data) {
        final E[] values = documentEntries.getEnumConstants();
        for(int i = 0; i < values.length; i++) {
            final Object value = data[i];
            if(value != null) {
                document.put(values[i].key(), value);
            }
        }
    }

    public abstract Class<? extends InsertDocumentEntry> getEntryClass();

    @Override
    public String toString() {
        return document.toString();
    }
}
