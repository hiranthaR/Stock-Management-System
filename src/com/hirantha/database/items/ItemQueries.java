package com.hirantha.database.items;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.models.data.item.Item;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemQueries {

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String ITEMS_COLLECTION = "items";
    private MongoCollection<Document> itemsMongoCollection = db.getCollection(ITEMS_COLLECTION);

    private String ID = "_id"; // == item code
    private String NAME = "name";
    private String CATEGORY = "category";
    private String UNIT = "unit";
    private String RECEIPT_PRICE = "receipt_price";
    private String MARKED_PRICE = "marked_price";
    private String SELLING_PRICE = "selling_price";
    private String PERCENTAGE = "percentage";
    private String RANK1 = "rank1";
    private String RANK2 = "rank2";
    private String RANK3 = "rank3";


    private static ItemQueries instance;

    private ItemQueries() {
    }

    public static ItemQueries getInstance() {
        if (instance == null) instance = new ItemQueries();
        return instance;
    }

    public void insertItem(Item item) {
        itemsMongoCollection.insertOne(new Document("unit", "kg"));
        itemsMongoCollection.insertOne(new Document("unit", "ml"));
        itemsMongoCollection.insertOne(new Document("unit", "l"));
    }

    public List<String> getUnits() {
        MongoCursor<Document> unitsFromDb = itemsMongoCollection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$unit"))
        )).iterator();

        List<String> units = new ArrayList<>();
        unitsFromDb.forEachRemaining(document -> units.add(document.get("_id").toString()));
        return units;
    }

    public List<String> getCategories() {
        MongoCursor<Document> categoriesFromDb = itemsMongoCollection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$category"))
        )).iterator();

        List<String> categories = new ArrayList<>();
        categoriesFromDb.forEachRemaining(document -> categories.add(document.get("_id").toString()));
        return categories;
    }
}
