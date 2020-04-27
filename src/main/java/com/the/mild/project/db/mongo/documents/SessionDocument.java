package com.the.mild.project.db.mongo.documents;

import com.the.mild.project.db.mongo.InsertDocument;
import com.the.mild.project.db.mongo.InsertDocumentEntry;
import com.the.mild.project.db.mongo.annotations.DocumentEntryKeys;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.server.jackson.SessionJson;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.the.mild.project.MongoCollections.SESSION_NAME;

@DocumentSerializable(collectionName = SESSION_NAME)
public class SessionDocument extends InsertDocument {

    public SessionDocument() {
        super();
    }

    public SessionDocument(SessionJson session) {
        super();
        putData(getEntryClass(), session.getGoogleId(), session.getEmail(), session.getExpirationTime());
    }

    @Override
    public Class<Entry> getEntryClass() {
        return Entry.class;
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @DocumentEntryKeys
    public enum Entry implements InsertDocumentEntry {
        GOOGLE_ID("googleId"),
        EMAIL("email"),
        EXPIRATION_TIME("expirationTime");

        private static final Map<String, Entry> ENTRY_BY_NAME = new HashMap<>();

        static {
            Arrays.asList(SessionDocument.Entry.values())
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
