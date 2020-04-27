package com.the.mild.project.db.mongo.documents;

import static com.the.mild.project.MongoCollections.TEST_NAME;

import java.util.function.Consumer;

import org.bson.Document;

import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.db.mongo.Query;
import com.the.mild.project.db.mongo.QueryDocument;

@DocumentSerializable(collectionName = TEST_NAME)
public class TestQueryDocument extends QueryDocument {

    private TestQueryDocument() {
        super();
    }

    @Override
    public Document getDocument() {
        return document;
    }

    public static final class Builder extends QueryDocument.Builder<TestQueryDocument> {
        private Query.Builder queryB = new Query.Builder();

        public Builder() {
            super(new TestQueryDocument());
        }

        public Builder addQuery(Query query) {
            super.putQueries(query);
            return this;
        }

        public Builder addQuery(Consumer<Query.Builder> mutator) {
            Query.Builder testQueryB = this.queryB;
            mutator.accept(testQueryB);
            super.putQuery(testQueryB.build());
            return this;
        }

        public Builder addQueries(Query... queries) {
            super.putQueries(queries);
            return this;
        }

        public Builder addQueries(Consumer<Query.Builder>... mutators) {
            final int len = mutators.length;
            final Query[] queries = new Query[len];

            for(int i = 0; i < len; i++) {
                final Consumer<Query.Builder> mutator = mutators[i];
                final Query.Builder testQueryB = this.queryB;

                mutator.accept(testQueryB);

                final Query query = testQueryB.build();
                queries[i] = query;
            }

            super.putQueries(queries);
            return this;
        }

        public TestQueryDocument build() {
            return super.build();
        }
    }
}
