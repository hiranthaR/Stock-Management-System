package com.hirantha.database.meta;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class MetaQueries {

    private static MetaQueries instance;

    private MetaQueries() {
    }

    public static MetaQueries getInstance() {
        if (instance == null) instance = new MetaQueries();
        return instance;
    }

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String META_COLLECTION = "meta";
    private MongoCollection<Document> metaMongoCollection = db.getCollection(META_COLLECTION);


    private String ID = "_id";

    private String CUSTOMER_DOCUMENT_ID = "customer_meta";
    private String NEXT_CUSTOMER_ID = "next_customer_id";

    private String ITEM_DOCUMENT_ID = "item_meta";
    private String NEXT_ITEM_ID = "next_item_id";

    private String INVOICE_DOCUMENT_ID = "invoice_meta";
    private String NEXT_INVOICE_ID = "next_invoice_id";

    public int getCustomerNextID() {
        int nextID;
        try {
            Document customerDocument = metaMongoCollection.find(Filters.eq(ID, CUSTOMER_DOCUMENT_ID)).first();

            nextID = customerDocument.getInteger(NEXT_CUSTOMER_ID);
            metaMongoCollection.updateOne(Filters.eq(ID, CUSTOMER_DOCUMENT_ID),
                    new BasicDBObject("$set", new BasicDBObject(NEXT_CUSTOMER_ID, nextID + 1)));

        } catch (NullPointerException e) {
            System.out.println("catch");
            nextID = 0;
            metaMongoCollection.insertOne(new Document(ID, CUSTOMER_DOCUMENT_ID)
                    .append(NEXT_CUSTOMER_ID, 1));
        }
        return nextID;
    }

    public int getItemNextID() {
        int nextID;
        try {
            Document itemDocument = metaMongoCollection.find(Filters.eq(ID, ITEM_DOCUMENT_ID)).first();

            nextID = itemDocument.getInteger(NEXT_ITEM_ID);
            metaMongoCollection.updateOne(Filters.eq(ID, ITEM_DOCUMENT_ID),
                    new BasicDBObject("$set", new BasicDBObject(NEXT_ITEM_ID, nextID + 1)));

        } catch (NullPointerException e) {
            System.out.println("catch");
            nextID = 0;
            metaMongoCollection.insertOne(new Document(ID, ITEM_DOCUMENT_ID)
                    .append(NEXT_ITEM_ID, 1));
        }
        return nextID;
    }

    public int getInvoiceNextID() {
        int nextID;
        try {
            Document invoiceDocument = metaMongoCollection.find(Filters.eq(ID, INVOICE_DOCUMENT_ID)).first();

            nextID = invoiceDocument.getInteger(NEXT_INVOICE_ID);
            metaMongoCollection.updateOne(Filters.eq(ID, INVOICE_DOCUMENT_ID),
                    new BasicDBObject("$set", new BasicDBObject(NEXT_INVOICE_ID, nextID + 1)));

        } catch (NullPointerException e) {
            System.out.println("catch");
            nextID = 0;
            metaMongoCollection.insertOne(new Document(ID, INVOICE_DOCUMENT_ID)
                    .append(NEXT_INVOICE_ID, 1));
        }
        return nextID;
    }
}
