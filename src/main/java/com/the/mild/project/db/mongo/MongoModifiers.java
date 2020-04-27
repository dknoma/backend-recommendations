package com.the.mild.project.db.mongo;

public enum MongoModifiers {
    SET("$set"),
    INC("$inc");

    private final String modifier;

    MongoModifiers(String modifier) {
        this.modifier = modifier;
    }

    public String getModifier() {
        return this.modifier;
    }
}
