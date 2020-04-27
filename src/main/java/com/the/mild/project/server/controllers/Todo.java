package com.the.mild.project.server.controllers;

import static com.the.mild.project.MongoCollections.*;
import static com.the.mild.project.ResourceConfig.PATH_CREATE;
import static com.the.mild.project.ResourceConfig.PATH_TODO_RESOURCE;
import static com.the.mild.project.ResourceConfig.PATH_UPDATE_BY_ID_PARAM;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;
import static com.the.mild.project.server.Main.MONGO_DB_FACTORY;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoDatabase;
import com.the.mild.project.MongoDatabaseType;
import com.the.mild.project.db.mongo.MongoDatabaseFactory;
import com.the.mild.project.db.mongo.MongoDocumentHandler;
import com.the.mild.project.db.mongo.documents.TodoDocument;
import com.the.mild.project.db.mongo.exceptions.CollectionNotFoundException;
import com.the.mild.project.db.mongo.exceptions.DocumentSerializationException;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.TodoJson;

/**
 * Root resource
 */
@Singleton
@Path(PATH_TODO_RESOURCE)
public class Todo {
    private static final MongoDatabaseFactory mongoFactory;
    private static final MongoDocumentHandler mongoHandlerDevelopTest;

    static {
        mongoFactory = MONGO_DB_FACTORY.orElse(null);

        assert mongoFactory != null;

        final MongoDatabase developTestDb = mongoFactory.getDatabase(MongoDatabaseType.DEVELOP_TEST);
        mongoHandlerDevelopTest = new MongoDocumentHandler(developTestDb);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     */
    @POST
    @Path(PATH_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTodo(String todoBody) {
        try {
            TodoJson todo = JacksonHandler.unmarshal(todoBody, TodoJson.class);
            final TodoDocument document = new TodoDocument(todo);

            mongoHandlerDevelopTest.tryInsert(document);
        } catch(JsonProcessingException | DocumentSerializationException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param id format = _id: ObjectId("5e4c9832489d4d3766a257f4")
     * @param todoBody
     */
    @PUT
    @Path(PATH_UPDATE_BY_ID_PARAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTodo(@PathParam(PATH_PARAM_ID) String id, String todoBody) {
        try {
            System.out.println(todoBody);
            Document updateDoc = JacksonHandler.stringToDocument(todoBody);

            mongoHandlerDevelopTest.tryUpdateOneById(TODO.collectionName(), id, updateDoc);
        } catch(JsonProcessingException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
    }
}
