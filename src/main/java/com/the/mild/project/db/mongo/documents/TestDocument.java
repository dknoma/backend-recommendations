package com.the.mild.project.db.mongo.documents;

import static com.the.mild.project.MongoCollections.TEST_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.the.mild.project.db.mongo.annotations.DocumentEntryKeys;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.db.mongo.InsertDocument;
import com.the.mild.project.db.mongo.InsertDocumentEntry;

@DocumentSerializable(collectionName = TEST_NAME)
public class TestDocument extends InsertDocument {

    public TestDocument() {
        super();
    }

    public TestDocument(long authorId, long channelId, long time, String timeUnit, String reminderMessage) {
        super();
        putData(getEntryClass(), authorId, channelId, time, timeUnit, reminderMessage);
    }

    /**
     * Put all required data into the document.
     * @param authorId;
     * @param channelId;
     * @param time;
     * @param timeUnit;
     * @param reminderMessage;
     * @return TestDocument
     */
    public TestDocument putAll(long authorId, long channelId, long time, String timeUnit, String reminderMessage) {
        putData(getEntryClass(), authorId, channelId, time, timeUnit, reminderMessage);
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
        AUTHOR_ID("authorId"),
        CHANNEL_ID("channelId"),
        TIME("time"),
        TIME_UNIT("unit"),
        REMINDER_MESSAGE("reminderMessage");

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
