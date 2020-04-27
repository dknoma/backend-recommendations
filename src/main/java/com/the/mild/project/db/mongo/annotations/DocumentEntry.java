package com.the.mild.project.db.mongo.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface DocumentEntry {
    String key() default "";
    Class getClassType() default Object.class;
}
