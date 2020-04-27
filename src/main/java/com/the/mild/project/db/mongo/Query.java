package com.the.mild.project.db.mongo;

public final class Query<K extends InsertDocumentEntry, V> {
    private K key;
    private V value;

    public Query() {
    }

    public Query(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }

    public static final class Builder<J extends InsertDocumentEntry, T> {
        private Query<J, T> query = new Query<>();

        public Builder() {

        }

        public Builder query(J key, T value) {
            this.query(key, value);
            return this;
        }

        public Builder key(J key) {
            this.query.key = key;
            return this;
        }

        public Builder value(T value) {
            this.query.value = value;
            return this;
        }

        public Query<J, T> build() {
            return query;
        }
    }
}
