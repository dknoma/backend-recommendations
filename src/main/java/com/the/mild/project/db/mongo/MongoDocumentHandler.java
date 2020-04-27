package com.the.mild.project.db.mongo;

import java.util.ArrayList;
import java.util.Objects;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.UpdateOptions;
import com.the.mild.project.db.mongo.annotations.DocumentEntryKeys;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.db.mongo.exceptions.CollectionNotFoundException;
import com.the.mild.project.db.mongo.exceptions.DocumentSerializationException;

public final class MongoDocumentHandler {

    private static final UpdateOptions updateOptions = new UpdateOptions();
    private static final FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();

    static {
        updateOptions.upsert(true);
        findOneAndUpdateOptions.upsert(true);
    }

    private final MongoDatabase database;

    public MongoDocumentHandler(MongoDatabase database) {
        this.database = database;
    }

    public FindIterable<Document> tryFindAll(QueryDocument document) throws DocumentSerializationException, CollectionNotFoundException {
        checkIfCanQueryDocument(document);

        final String collectionName = getCollectionName(document);
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final Document doc = document.getDocument();

        return collection.find(doc);
    }

    public Document tryFindById(String collectionName, String id) throws CollectionNotFoundException {
        final Document query = new Document();
        query.put("_id", id);

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final FindIterable<Document> documents = collection.find(query);
        final Document next = documents.iterator()
                                       .next();
        return next;
    }

    public Document tryFindOne(String collectionName, DocumentEntry<?>... entries) throws CollectionNotFoundException {
        final Document query = new Document();
        for(DocumentEntry<?> entry : entries) {
            query.put(entry.getKey(), entry.getValue());
        }

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final FindIterable<Document> documents = collection.find(query);
        final Document next = documents.iterator()
                                       .next();
        return next;
    }

    public void tryUpdateOne(String collectionName, Document original, Document update) throws CollectionNotFoundException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        collection.updateOne(original, new Document(MongoModifiers.SET.getModifier(), update));
    }

    public void tryUpdateOne(String collectionName, Document original, DocumentEntry<?>... entries) throws CollectionNotFoundException {
        final Document update = new Document();
        final Document set = new Document();
        for(DocumentEntry<?> entry : entries) {
            set.put(entry.getKey(), entry.getValue());
        }
        update.put(MongoModifiers.SET.getModifier(), set);

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        collection.updateOne(original, update, updateOptions);
    }

    public void tryUpdateOneById(String collectionName, String id, DocumentEntry<?>... entries) throws CollectionNotFoundException {
        final ObjectId objectId = new ObjectId(id);
        final Document update = new Document();
        final Document set = new Document();
        for(DocumentEntry<?> entry : entries) {
            set.put(entry.getKey(), entry.getValue());
        }
        update.put(MongoModifiers.SET.getModifier(), set);

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final FindIterable<Document> documents = collection.find(new Document("_id", objectId));
        final MongoCursor<Document> iterator = documents.iterator();

        if(iterator.hasNext()) {
            collection.updateOne(iterator.next(), update, updateOptions);
        }
    }

    public void tryUpdateOneById(String collectionName, String id, InsertDocument update) throws CollectionNotFoundException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final ObjectId objectId = new ObjectId(id);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final Document set = new Document(MongoModifiers.SET.getModifier(), update.getDocument());
        final Document d = collection.findOneAndUpdate(new Document("_id", objectId), set, findOneAndUpdateOptions);
        System.out.printf("d=%s\n", d);
    }

    public void tryUpdateOneById(String collectionName, String id, Document update) throws CollectionNotFoundException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final ObjectId objectId = new ObjectId(id);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final Document set = new Document(MongoModifiers.SET.getModifier(), update);
        final Document d = collection.findOneAndUpdate(new Document("_id", objectId), set, findOneAndUpdateOptions);
        System.out.printf("d=%s\n", d);
    }

    /**
     * Tries to write a document to the database.
     *
     * @param document document
     * @return this
     * @throws DocumentSerializationException If the document is not annotated correctly.
     * @throws CollectionNotFoundException If the collection name was not found in the database.
     */
    public MongoDocumentHandler tryInsert(InsertDocument document) throws DocumentSerializationException, CollectionNotFoundException {
        checkIfCanInsertDocument(document);

        final String collectionName = getCollectionName(document);
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final Document doc = document.getDocument();
        collection.insertOne(doc);

        return this;
    }

    /**
     * Inserts a new document into a given collection.
     *
     * @param collectionName
     * @param document
     * @return
     * @throws DocumentSerializationException
     * @throws CollectionNotFoundException
     */
    public MongoDocumentHandler tryInsert(String collectionName, InsertDocument document) throws DocumentSerializationException, CollectionNotFoundException {
        checkIfCanInsertDocument(document);

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        final Document doc = document.getDocument();
        collection.insertOne(doc);

        return this;
    }

    /**
     * Insert a new document into a given collection.
     *
     * @param collectionName
     * @param document
     * @return
     * @throws DocumentSerializationException
     * @throws CollectionNotFoundException
     */
    public MongoDocumentHandler tryInsert(String collectionName, Document document) throws DocumentSerializationException, CollectionNotFoundException {

        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if(collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        FindIterable docs = collection.find(document);
        if (docs.first() == null) {
//            throw new DocumentSerializationException("Document already exists in collection");
            collection.insertOne(document);
        }

        return this;
    }

    /**
     * Get all users in the database, used for admin panel so admins can add or remove users.
     *
     * @param collectionName
     * @return
     * @throws CollectionNotFoundException
     */
    public Document getAllUsers(String collectionName) throws CollectionNotFoundException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if (collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        ArrayList<Document> userDocs = new ArrayList<>();
        for (Document doc: collection.find()) {
            String docId = (String) doc.get("_id");
            doc.put(("id"), docId);
            doc.remove("_id");
            userDocs.add(doc);
        }

        Document allUsers = new Document();
        allUsers.put("users", userDocs);
        return allUsers;
    }

    /**
     * Deletes a given document from a specified collection if it exists.
     *
     * @param collectionName
     * @param document
     * @return deleted document or null if no document was found
     * @throws CollectionNotFoundException
     */
    public Document tryDelete(String collectionName, Document document) throws CollectionNotFoundException, DocumentSerializationException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        if (collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        Document session = collection.find(document).first();
        if (session == null) {
            throw new DocumentSerializationException("Document does not exist");
        }
        return collection.findOneAndDelete(session);
    }

    /**
     * Deletes a given record based on their ID in a given collection.
     *
     * @param collectionName
     * @param id
     * @return
     * @throws CollectionNotFoundException
     * @throws DocumentSerializationException
     */
    public Document tryDelete(String collectionName, String id) throws CollectionNotFoundException, DocumentSerializationException {
        final MongoCollection<Document> collection = database.getCollection(collectionName);

        Document document = new Document();
        document.put("_id", id);

        if (collection == null) {
            throw new CollectionNotFoundException(String.format("Collection %s was not found in the database.", collectionName));
        }

        Document session = collection.find(document).first();
        if (session == null) {
            throw new DocumentSerializationException("Document does not exist");
        }
        return collection.findOneAndDelete(session);
    }

    private void checkIfCanQueryDocument(QueryDocument document) throws DocumentSerializationException {
        final boolean isNull = checkIfNull(document);
        if(isNull) {
            throw new DocumentSerializationException("The object to serialize is null");
        }

        final Validity validDoc = checkIfSerializable(document);
        if(!validDoc.valid) {
            throw new DocumentSerializationException(validDoc.message);
        }
    }

    private void checkIfCanInsertDocument(InsertDocument document) throws DocumentSerializationException {
        final boolean isNull = checkIfNull(document);
        if(isNull) {
            throw new DocumentSerializationException("The object to serialize is null");
        }

        final Validity validDoc = checkIfSerializable(document);
        if(!validDoc.valid) {
            throw new DocumentSerializationException(validDoc.message);
        }

        final Validity validKeys = checkIfHasKeys(document);
        if(!validKeys.valid) {
            throw new DocumentSerializationException(validKeys.message);
        }
    }

    private boolean checkIfNull(BaseDocument document) {
        return Objects.isNull(document);
    }

    private Validity checkIfSerializable(BaseDocument document) throws DocumentSerializationException {
        final Validity validity = new Validity();
        final Class<?> dClass = document.getClass();

        if (!dClass.isAnnotationPresent(DocumentSerializable.class)) {
            validity.valid = false;
            validity.message = String.format("The class %s is not annotated with DocumentSerializable.",
                                               dClass.getSimpleName());
        }

        return validity;
    }

    private Validity checkIfHasKeys(InsertDocument document) throws DocumentSerializationException {
        final Validity validity = new Validity();
        final Class<? extends InsertDocumentEntry> entryClass = document.getEntryClass();

        if (!entryClass.isAnnotationPresent(DocumentEntryKeys.class)) {
            validity.valid = false;
            validity.message = String.format("The class %s is not annotated with DocumentEntryKeys.",
                                             entryClass.getSimpleName());
        }

        return validity;
    }

    /**
     * Gets the name of the MongoDb Collection that the document belongs to
     * @param document document
     * @return collectionName
     */
    private String getCollectionName(BaseDocument document) {
        final Class<?> dClass = document.getClass();
        final DocumentSerializable annotation = dClass.getAnnotation(DocumentSerializable.class);

        return annotation.collectionName();
    }

    private final class Validity {
        private boolean valid;
        private String message;

        private Validity() {
            this.valid = true;
            this.message = "";
        }
    }
}
