package com.the.mild.project.db.mongo;

import org.bson.Document;

public abstract class QueryDocument implements BaseDocument {
    protected Document document;

    protected QueryDocument() {
        this.document = new Document();
    }

    protected void putQuery(Query query) {
        document.put(query.key().key(), query.value());
    }

    protected void putQueries(Query... queries) {
        for(final Query query : queries) {
            document.put(query.key().key(), query.value());
        }
    }

    public Document document() {
        return this.document;
    }

    public abstract static class Builder<T extends QueryDocument> {
        private final T queryDocument;

        protected Builder(T queryDocument) {
            this.queryDocument = queryDocument;
        }

        public Builder putQuery(Query query) {
            queryDocument.putQuery(query);
            return this;
        }

        public Builder putQueries(Query... queries) {
            queryDocument.putQueries(queries);
            return this;
        }

        public T build() {
            return queryDocument;
        }
    }
}
