package com.hirantha.database.customers;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.customer.Customer;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CustomerQueries {

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String CUSTOMERS_COLLECTION = "customers";
    private MongoCollection<Document> customersMongoCollection = db.getCollection(CUSTOMERS_COLLECTION);

    private String ID = "_id";
    private String NAME = "name";
    private String ADDRESS = "address";
    private String TELEPHONE = "telephone";
    private String TITLE = "title";
    private String RANK = "rank";
    private String IMAGE_URL = "image_url";

    private static CustomerQueries instance;

    private CustomerQueries() {
    }

    public static CustomerQueries getInstance() {
        if (instance == null) instance = new CustomerQueries();
        return instance;
    }


    public void insertCustomer(Customer customer) {
        int id = MetaQueries.getInstance().getCustomerNextID();
        Document customerDocument = new Document(ID, "c" + StringUtils.leftPad(String.valueOf(id), 4, "0"))
                .append(TITLE, customer.getTitle())
                .append(NAME, customer.getName())
                .append(RANK, customer.getRank())
                .append(ADDRESS, customer.getAddress())
                .append(IMAGE_URL, customer.getImageUrl())
                .append(TELEPHONE, customer.getTelephone());

        customersMongoCollection.insertOne(customerDocument);
    }

    public void updateCustomer(Customer customer) {

        BasicDBObject newDataDocument = new BasicDBObject("$set",
                new BasicDBObject(TITLE, customer.getTitle())
                        .append(NAME, customer.getName())
                        .append(RANK, customer.getRank())
                        .append(ADDRESS, customer.getAddress())
                        .append(IMAGE_URL, customer.getImageUrl())
                        .append(TELEPHONE, customer.getTelephone()));

        customersMongoCollection.updateOne(Filters.eq(ID, customer.getId()), newDataDocument);
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        FindIterable<Document> customersResults = customersMongoCollection.find();
        customersResults.sort(new BasicDBObject(NAME, 1));
        customersResults.iterator().forEachRemaining(document -> customers.add(gson.fromJson(document.toJson(), Customer.class)));

        return customers;
    }

    public void deleteCustomer(Customer customer) {
        customersMongoCollection.deleteOne(Filters.eq(ID, customer.getId()));
    }

    public Customer getCustomer(String id) {
        return gson.fromJson(customersMongoCollection.find(Filters.eq(ID, id)).first().toJson(), Customer.class);
    }
}
