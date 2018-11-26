package com.hirantha.database.invoice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.invoice.Invoice;
import com.hirantha.models.data.invoice.Supplier;
import com.hirantha.models.data.item.InvoiceTableItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceQueries {

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String INVOICES_COLLECTION = "invoices";
    private MongoCollection<Document> invoicesMongoCollection = db.getCollection(INVOICES_COLLECTION);

    private static InvoiceQueries instance;

    private InvoiceQueries() {
    }

    public static InvoiceQueries getInstance() {
        if (instance == null) instance = new InvoiceQueries();
        return instance;
    }

    private String INVOICE_ID = "_id";
    private String DATE = "date";
    private String SUPPLIER_INVOICE_NUMBER = "invoiceNumber";
    private String SUPPLIER_NAME = "name";
    private String SUPPLIER_ADDRESS = "address";
    private String ITEMS = "tableItems";
    private String TOTAL_BILL_COST = "billCost";
    private String ITEM_CODE = "itemId";
    private String ITEM_NAME = "name";
    private String ITEM_QUANTITY = "quantity";
    private String COST_PER_ITEM = "costPerItem";
    private String ITEM_UNIT = "unit";
    private String CASH = "cash";
    private String BANK = "bank";
    private String BRANCH = "branch";
    private String CHEQUE_DATE = "chequeDate";
    private String AMOUNT = "amount";
    private String PREPARED_ADMIN_NAME = "preparedAdminName";
    private String PREPARED_ADMIN_ID = "preparedAdminId";
    private String ACCEPTED_ADMIN_NAME = "acceptedAdminName";
    private String ACCEPTED_ADMIN_ID = "acceptedAdminId";
    private String CHECKED_ADMIN_NAME = "checkedAdminName";
    private String CHECKED_ADMIN_ID = "checkedAdminId";


    public void insertInvoice(Invoice invoice) {
        int id = MetaQueries.getInstance().getInvoiceNextID();

        List<DBObject> tableItemList = new ArrayList<>();
        invoice.getInvoiceTableItems().forEach(e -> tableItemList.add(new BasicDBObject(ITEM_CODE, e.getItemId())
                .append(ITEM_NAME, e.getName())
                .append(ITEM_UNIT, e.getUnit())
                .append(ITEM_QUANTITY, e.getQuantity())
                .append(COST_PER_ITEM, e.getCostPerItem())));

        Document invoiceDocument = new Document(INVOICE_ID, "in" + StringUtils.leftPad(String.valueOf(id), 4, "0"))
                .append(DATE, invoice.getDate())
                .append(SUPPLIER_NAME, invoice.getName())
                .append(SUPPLIER_ADDRESS, invoice.getAddress())
                .append(SUPPLIER_INVOICE_NUMBER, invoice.getInvoiceNumber())
                .append(ITEMS, tableItemList)
                .append(TOTAL_BILL_COST, invoice.getBillCost())
                .append(CASH, invoice.isCash())
                .append(BANK, invoice.getBank())
                .append(BRANCH, invoice.getBranch())
                .append(AMOUNT, invoice.getAmount())
                .append(CHEQUE_DATE, invoice.getChequeDate())
                .append(PREPARED_ADMIN_ID, invoice.getPreparedAdminId())
                .append(PREPARED_ADMIN_NAME, invoice.getPreparedAdminName())
                .append(ACCEPTED_ADMIN_ID, invoice.getAcceptedAdminId())
                .append(ACCEPTED_ADMIN_NAME, invoice.getAcceptedAdminName())
                .append(CHECKED_ADMIN_ID, invoice.getCheckedAdminId())
                .append(CHECKED_ADMIN_NAME, invoice.getCheckedAdminName());

        invoicesMongoCollection.insertOne(invoiceDocument);
    }

    public void updateInvoice(Invoice invoice) {

        List<DBObject> tableItemList = new ArrayList<>();
        invoice.getInvoiceTableItems().forEach(e -> tableItemList.add(new BasicDBObject(ITEM_CODE, e.getItemId())
                .append(ITEM_NAME, e.getName())
                .append(ITEM_UNIT, e.getUnit())
                .append(ITEM_QUANTITY, e.getQuantity())
                .append(COST_PER_ITEM, e.getCostPerItem())));

        BasicDBObject newDataDocument = new BasicDBObject("$set",
                new BasicDBObject()
                        .append(DATE, invoice.getDate())
                        .append(SUPPLIER_NAME, invoice.getName())
                        .append(SUPPLIER_ADDRESS, invoice.getAddress())
                        .append(SUPPLIER_INVOICE_NUMBER, invoice.getInvoiceNumber())
                        .append(ITEMS, tableItemList)
                        .append(TOTAL_BILL_COST, invoice.getBillCost())
                        .append(CASH, invoice.isCash())
                        .append(BANK, invoice.getBank())
                        .append(BRANCH, invoice.getBranch())
                        .append(AMOUNT, invoice.getAmount())
                        .append(CHEQUE_DATE, invoice.getChequeDate())
                        .append(PREPARED_ADMIN_ID, invoice.getPreparedAdminId())
                        .append(PREPARED_ADMIN_NAME, invoice.getPreparedAdminName())
                        .append(ACCEPTED_ADMIN_ID, invoice.getAcceptedAdminId())
                        .append(ACCEPTED_ADMIN_NAME, invoice.getAcceptedAdminName())
                        .append(CHECKED_ADMIN_ID, invoice.getCheckedAdminId())
                        .append(CHECKED_ADMIN_NAME, invoice.getCheckedAdminName()));

        invoicesMongoCollection.updateOne(Filters.eq(INVOICE_ID, invoice.get_id()), newDataDocument);
    }

    public List<Supplier> getSuppliers() {

        List<Supplier> suppliers = new ArrayList<>();

        String projection = "{_id:0," + SUPPLIER_NAME + ":1," + SUPPLIER_ADDRESS + ":1}";
        invoicesMongoCollection.find().projection(BasicDBObject.parse(projection)).iterator().forEachRemaining(e -> suppliers.add(gson.fromJson(e.toJson(), Supplier.class)));

        return suppliers;
    }

    public List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList<>();

        FindIterable<Document> itemsResults = invoicesMongoCollection.find();
        itemsResults.sort(new BasicDBObject(DATE, 1));
        itemsResults.iterator().forEachRemaining(document -> invoices.add(createInvoice(document)));

        return invoices;
    }

    public List<InvoiceTableItem> getInvoiceTableItems() {
        List<InvoiceTableItem> invoiceTableItems = new ArrayList<>();

        String projection = "{_id:0," + ITEMS + ":1}";
        List<Document> itemsListOfList = invoicesMongoCollection.find().projection(BasicDBObject.parse(projection)).into(new ArrayList<>());
        for (Document itemsList : itemsListOfList) {
            List<Document> itemList = (List<Document>) itemsList.get("tableItems");
            for (Document item : itemList) {
                String itemId = item.getString(ITEM_CODE);
                String name = item.getString(ITEM_NAME);
                String unit = item.getString(ITEM_UNIT);
                int quantity = item.getInteger(ITEM_QUANTITY);
                double costPerItem = item.getDouble(COST_PER_ITEM);
                invoiceTableItems.add(new InvoiceTableItem(itemId, name, unit, quantity, costPerItem));
            }
        }
        return invoiceTableItems;
    }

    private Invoice createInvoice(Document document) {
        String id = document.getString(INVOICE_ID);
        Date date = document.getDate(DATE);
        String invoiceNumber = document.getString(SUPPLIER_INVOICE_NUMBER);
        String supplierName = document.getString(SUPPLIER_NAME);
        String supplierAddress = document.getString(SUPPLIER_ADDRESS);
        List<InvoiceTableItem> invoiceTableItems = new ArrayList<>();
        ArrayList<Document> itemsList = (ArrayList<Document>) document.get(ITEMS);
        itemsList.forEach(e -> {

            String itemId = e.getString(ITEM_CODE);
            String itemName = e.getString(ITEM_NAME);
            String unit = e.getString(ITEM_UNIT);
            double costPerItem = e.getDouble(COST_PER_ITEM);
            int quantity = e.getInteger(ITEM_QUANTITY);

            invoiceTableItems.add(new InvoiceTableItem(itemId, itemName, unit, quantity, costPerItem));
        });
        double billCost = document.getDouble(TOTAL_BILL_COST);
        boolean cash = document.getBoolean(CASH);

        String bank = null, branch = null;
        Date chequeDate = null;
        double amount = 0;

        if (!cash) {
            bank = document.getString(BANK);
            branch = document.getString(BRANCH);
            chequeDate = document.getDate(CHEQUE_DATE);
            amount = document.getDouble(AMOUNT);
        }

        String preparedAdminName = document.getString(PREPARED_ADMIN_NAME);
        String preparedAdminId = document.getString(PREPARED_ADMIN_ID);
        String acceptedAdminName = document.getString(ACCEPTED_ADMIN_NAME);
        String acceptedAdminId = document.getString(ACCEPTED_ADMIN_ID);
        String checkedAdminName = document.getString(CHECKED_ADMIN_NAME);
        String checkedAdminId = document.getString(CHECKED_ADMIN_ID);

        return new Invoice(id, date, invoiceNumber, supplierName, supplierAddress, invoiceTableItems, billCost, cash, bank, branch, chequeDate, amount, preparedAdminName, preparedAdminId, checkedAdminName, checkedAdminId, acceptedAdminName, acceptedAdminId);
    }

    public void deleteInvoice(Invoice invoice) {
        invoicesMongoCollection.deleteOne(Filters.eq(INVOICE_ID, invoice.get_id()));
    }

}
