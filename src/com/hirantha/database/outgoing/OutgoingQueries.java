package com.hirantha.database.outgoing;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.item.BillTableItem;
import com.hirantha.models.data.outgoing.Bill;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutgoingQueries {

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String INVOICES_COLLECTION = "outgoing_bills";
    private MongoCollection<Document> billsMongoCollection = db.getCollection(INVOICES_COLLECTION);

    private static OutgoingQueries instance;

    private OutgoingQueries() {
    }

    public static OutgoingQueries getInstance() {
        if (instance == null) instance = new OutgoingQueries();
        return instance;
    }

    private String INVOICE_ID = "_id";
    private String DATE = "date";
    private String CUSTOMER_ID = "id";
    private String CUSTOMER_NAME = "customer_name";
    private String CUSTOMER_ADDRESS = "customer_address";
    private String CUSTOMER_RANK = "customer_rank";
    private String ITEMS = "tableItems";
    private String TOTAL_BILL_COST = "billCost";
    private String ITEM_CODE = "itemId";
    private String ITEM_NAME = "name";
    private String ITEM_QUANTITY = "quantity";
    private String COST_PER_ITEM = "costPerItem";
    private String DISCOUNT = "discount";
    private String PERCENTAGE = "percentage";
    private String ITEM_UNIT = "unit";
    private String PREPARED_ADMIN_NAME = "preparedAdminName";
    private String PREPARED_ADMIN_ID = "preparedAdminId";
    private String ACCEPTED_ADMIN_NAME = "acceptedAdminName";
    private String ACCEPTED_ADMIN_ID = "acceptedAdminId";
    private String CHECKED_ADMIN_NAME = "checkedAdminName";
    private String CHECKED_ADMIN_ID = "checkedAdminId";
    private String VEHICLE_NO = "vehicle_number";


    public void insertBill(Bill bill) {
        int id = MetaQueries.getInstance().getBillNextID();

        List<DBObject> tableItemList = new ArrayList<>();
        bill.getTableItems().forEach(e -> tableItemList.add(new BasicDBObject(ITEM_CODE, e.getItemId())
                .append(ITEM_NAME, e.getName())
                .append(ITEM_UNIT, e.getUnit())
                .append(ITEM_QUANTITY, e.getQuantity())
                .append(PERCENTAGE, e.isPercentage())
                .append(DISCOUNT, e.getDiscount())
                .append(COST_PER_ITEM, e.getCostPerItem())));

        Document billDocument = new Document(INVOICE_ID, "bill" + StringUtils.leftPad(String.valueOf(id), 4, "0"))
                .append(DATE, bill.getDate())
                .append(CUSTOMER_ID, bill.getCustomerId())
                .append(CUSTOMER_NAME, bill.getCustomerName())
                .append(CUSTOMER_ADDRESS, bill.getCustomerAddress())
                .append(CUSTOMER_RANK, bill.getCustomerRank())
                .append(ITEMS, tableItemList)
                .append(TOTAL_BILL_COST, bill.getTotalBillCost())
                .append(PREPARED_ADMIN_ID, bill.getPreparedAdminId())
                .append(PREPARED_ADMIN_NAME, bill.getPreparedAdminName())
                .append(ACCEPTED_ADMIN_ID, bill.getAcceptedAdminId())
                .append(ACCEPTED_ADMIN_NAME, bill.getAcceptedAdminName())
                .append(CHECKED_ADMIN_ID, bill.getCheckedAdminId())
                .append(CHECKED_ADMIN_NAME, bill.getCheckedAdminName())
                .append(VEHICLE_NO, bill.getVehicleNumber());

        billsMongoCollection.insertOne(billDocument);
    }

    public void updateInvoice(Bill bill) {

        System.out.println(bill.get_id());

        List<DBObject> tableItemList = new ArrayList<>();
        bill.getTableItems().forEach(e -> tableItemList.add(new BasicDBObject(ITEM_CODE, e.getItemId())
                .append(ITEM_NAME, e.getName())
                .append(ITEM_UNIT, e.getUnit())
                .append(ITEM_QUANTITY, e.getQuantity())
                .append(PERCENTAGE, e.isPercentage())
                .append(DISCOUNT, e.getDiscount())
                .append(COST_PER_ITEM, e.getCostPerItem())));

        BasicDBObject newDataDocument = new BasicDBObject("$set",
                new BasicDBObject()
                        .append(DATE, bill.getDate())
                        .append(CUSTOMER_ID, bill.getCustomerId())
                        .append(CUSTOMER_NAME, bill.getCustomerName())
                        .append(CUSTOMER_ADDRESS, bill.getCustomerAddress())
                        .append(CUSTOMER_RANK, bill.getCustomerRank())
                        .append(ITEMS, tableItemList)
                        .append(TOTAL_BILL_COST, bill.getTotalBillCost())
                        .append(PREPARED_ADMIN_ID, bill.getPreparedAdminId())
                        .append(PREPARED_ADMIN_NAME, bill.getPreparedAdminName())
                        .append(ACCEPTED_ADMIN_ID, bill.getAcceptedAdminId())
                        .append(ACCEPTED_ADMIN_NAME, bill.getAcceptedAdminName())
                        .append(CHECKED_ADMIN_ID, bill.getCheckedAdminId())
                        .append(CHECKED_ADMIN_NAME, bill.getCheckedAdminName())
                        .append(VEHICLE_NO, bill.getVehicleNumber()));

        UpdateResult result = billsMongoCollection.updateOne(Filters.eq(INVOICE_ID, bill.get_id()), newDataDocument);
        System.out.println(result.getModifiedCount());
        System.out.println(result.getMatchedCount());
    }

    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();

        FindIterable<Document> itemsResults = billsMongoCollection.find();
        itemsResults.sort(new BasicDBObject(DATE, 1));
        itemsResults.iterator().forEachRemaining(document -> bills.add(createBill(document)));

        return bills;
    }

    private Bill createBill(Document document) {
        String id = document.getString(INVOICE_ID);
        Date date = document.getDate(DATE);
        String customerId = document.getString(CUSTOMER_ID);
        String customerName = document.getString(CUSTOMER_NAME);
        String customerAddress = document.getString(CUSTOMER_ADDRESS);
        int customerRank = document.getInteger(CUSTOMER_RANK);
        List<BillTableItem> tableItems = new ArrayList<>();
        ArrayList<Document> itemsList = (ArrayList<Document>) document.get(ITEMS);
        itemsList.forEach(e -> {
            String itemId = e.getString(ITEM_CODE);
            String itemName = e.getString(ITEM_NAME);
            String unit = e.getString(ITEM_UNIT);
            double costPerItem = e.getDouble(COST_PER_ITEM);
            boolean percentage = e.getBoolean(PERCENTAGE);
            double discount = e.getDouble(DISCOUNT);
            int quantity = e.getInteger(ITEM_QUANTITY);

            tableItems.add(new BillTableItem(itemId, itemName, unit, quantity, costPerItem, discount, percentage));
        });
        double billCost = document.getDouble(TOTAL_BILL_COST);

        String preparedAdminName = document.getString(PREPARED_ADMIN_NAME);
        String preparedAdminId = document.getString(PREPARED_ADMIN_ID);
        String acceptedAdminName = document.getString(ACCEPTED_ADMIN_NAME);
        String acceptedAdminId = document.getString(ACCEPTED_ADMIN_ID);
        String checkedAdminName = document.getString(CHECKED_ADMIN_NAME);
        String checkedAdminId = document.getString(CHECKED_ADMIN_ID);

        String vehicleNumber = document.getString(VEHICLE_NO);

        return new Bill(id, date, customerId, customerName, customerAddress, customerRank, tableItems, billCost, preparedAdminName, preparedAdminId, checkedAdminName, checkedAdminId, acceptedAdminName, acceptedAdminId, vehicleNumber);
    }

    public List<BillTableItem> getBillTableItems() {
        List<BillTableItem> billTableItems = new ArrayList<>();

        String projection = "{_id:0," + ITEMS + ":1}";
        List<Document> itemsListOfList = billsMongoCollection.find().projection(BasicDBObject.parse(projection)).into(new ArrayList<>());
        for (Document itemsList : itemsListOfList) {
            List<Document> itemList = (List<Document>) itemsList.get(ITEMS);
            for (Document item : itemList) {
                String itemId = item.getString(ITEM_CODE);
                String name = item.getString(ITEM_NAME);
                String unit = item.getString(ITEM_UNIT);
                int quantity = item.getInteger(ITEM_QUANTITY);
                double costPerItem = item.getDouble(COST_PER_ITEM);
                boolean percentage = item.getBoolean(PERCENTAGE);
                double discount = item.getDouble(DISCOUNT);
                billTableItems.add(new BillTableItem(itemId, name, unit, quantity, costPerItem, discount, percentage));
            }
        }
        return billTableItems;
    }

    public void deleteBill(Bill bill) {
        billsMongoCollection.deleteOne(Filters.eq(INVOICE_ID, bill.get_id()));
    }
}
