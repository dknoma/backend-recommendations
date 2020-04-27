package com.the.mild.project.db.mongo.documents;

import static com.the.mild.project.MongoCollections.TODO_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.the.mild.project.db.mongo.InsertDocument;
import com.the.mild.project.db.mongo.InsertDocumentEntry;
import com.the.mild.project.db.mongo.annotations.DocumentEntryKeys;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.server.jackson.TodoJson;

@DocumentSerializable(collectionName = TODO_NAME)
public class TodoDocument extends InsertDocument {

    public TodoDocument() {
        super();
    }

    public TodoDocument(TodoJson todo) {
        super();
        putData(getEntryClass(), todo.getUsername(), todo.getMessage(), todo.isCompleted());
    }

    public TodoDocument(String username, String message, boolean completed) {
        super();
        putData(getEntryClass(), username, message, completed);
    }

    /**
     *
     * Put all required data into the document.
     * @param username;
     * @param message;
     * @param completed;
     * @return document
     */
    public TodoDocument putAll(String username, String message, boolean completed) {
        putData(getEntryClass(), username, message, completed);
        return this;
    }

    /**
     * Returns the document.
     * @return document
     */
    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public Class<Entry> getEntryClass() {
        return Entry.class;
    }

    @DocumentEntryKeys
    public enum Entry implements InsertDocumentEntry {
        USERNAME("username"),
        TODO_MESSAGE("message"),
        COMPLETED("completed");

        private static final Map<String, Entry> ENTRY_BY_NAME = new HashMap<>();

        static {
            Arrays.asList(Entry.values())
                  .forEach(e -> ENTRY_BY_NAME.put(e.key(), e));
        }

        final private String key;

        Entry(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }

        public static Entry getByKeyName(String key) {
            return ENTRY_BY_NAME.getOrDefault(key, null);
        }
    }
}
