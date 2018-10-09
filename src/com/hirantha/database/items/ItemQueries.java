package com.hirantha.database.items;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.customer.Customer;
import com.hirantha.models.data.item.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        int id = MetaQueries.getInstance().getItemNextID();
        Document itemDocument = new Document(ID, "i" + StringUtils.leftPad(String.valueOf(id), 4, "0"))
                .append(NAME, item.getName())
                .append(CATEGORY, item.getCategory())
                .append(UNIT, item.getUnit())
                .append(RECEIPT_PRICE, item.getReceiptPrice())
                .append(MARKED_PRICE, item.getMarkedPrice())
                .append(SELLING_PRICE, item.getSellingPrice())
                .append(PERCENTAGE, item.isPercentage())
                .append(RANK1, item.getRank1())
                .append(RANK2, item.getRank2())
                .append(RANK3, item.getRank3());
        itemsMongoCollection.insertOne(itemDocument);
    }

    public List<String> getUnits() {
        MongoCursor<Document> unitsFromDb = itemsMongoCollection.aggregate(Collections.singletonList(
                new Document("$group", new Document("_id", "$unit"))
        )).iterator();

        List<String> units = new ArrayList<>();
        unitsFromDb.forEachRemaining(document -> {
            units.add(document.get(ID).toString());
        });
        return units;
    }

    public List<String> getCategories() {
        MongoCursor<Document> categoriesFromDb = itemsMongoCollection.aggregate(Collections.singletonList(
                new Document("$group", new Document("_id", "$category"))
        )).iterator();

        List<String> categories = new ArrayList<>();
        categoriesFromDb.forEachRemaining(document -> {
            categories.add(document.get(ID).toString());
        });
        return categories;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        FindIterable<Document> itemsResults = itemsMongoCollection.find();
        itemsResults.sort(new BasicDBObject(NAME, 1));
        itemsResults.iterator().forEachRemaining(document -> items.add(gson.fromJson(document.toJson(), Item.class)));

        return items;
    }

    public void deleteItem(Item item) {
        itemsMongoCollection.deleteOne(Filters.eq(ID, item.getItemCode()));
    }

    public void updateItem(Item item) {

        BasicDBObject newDataDocument = new BasicDBObject("$set",
                new BasicDBObject(NAME, item.getName())
                        .append(CATEGORY, item.getCategory())
                        .append(UNIT, item.getUnit())
                        .append(RECEIPT_PRICE, item.getReceiptPrice())
                        .append(MARKED_PRICE, item.getMarkedPrice())
                        .append(SELLING_PRICE, item.getSellingPrice())
                        .append(PERCENTAGE, item.isPercentage())
                        .append(RANK1, item.getRank1())
                        .append(RANK2, item.getRank2())
                        .append(RANK3, item.getRank3()));

        System.out.println(item.getItemCode());

        UpdateResult result = itemsMongoCollection.updateOne(Filters.eq(ID, item.getItemCode()), newDataDocument);
        System.out.println(result.getMatchedCount());
        System.out.println(result.getModifiedCount());
    }
}
