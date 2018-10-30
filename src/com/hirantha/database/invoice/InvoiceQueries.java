package com.hirantha.database.invoice;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.invoice.Invoice;
import com.hirantha.models.data.invoice.Supplier;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
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
    private String SUPPLIER_INVOICE_NUMBER = "supplier_invoice_number";
    private String SUPPLIER_NAME = "supplier_name";
    private String SUPPLIER_ADDRESS = "supplier_address";
    private String ITEMS = "items";
    private String ITEM_CODE = "item_code";
    private String ITEM_NAME = "item_name";
    private String ITEM_QUANTITY = "item_quantity";
    private String COST_PER_ITEM = "cost_per_item";
    private String ITEM_UNIT = "item_unit";
    private String TOTAL_BILL_COST = "total_bill_cost";
    private String CASH = "cash";
    private String BANK = "bank";
    private String BRANCH = "branch";
    private String CHEQUE_DATE = "cheque_date";
    private String AMOUNT = "amount";
    private String PREPARED_ADMIN_NAME = "prepared_admin_name";
    private String PREPARED_ADMIN_ID = "prepared_admin_id";
    private String ACCEPTED_ADMIN_NAME = "accepted_admin_name";
    private String ACCEPTED_ADMIN_ID = "accepted_admin_id";
    private String CHECKED_ADMIN_NAME = "checked_admin_name";
    private String CHECKED_ADMIN_ID = "checked_admin_id";


    public void insertInvoice(Invoice invoice) {
        int id = MetaQueries.getInstance().getInvoiceNextID();

        List<DBObject> tableItemList = new ArrayList<>();
        invoice.getTableItems().forEach(e -> tableItemList.add(new BasicDBObject(ITEM_CODE, e.getItemId())
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

    public List<Supplier> getSuppliers() {

        List<Supplier> suppliers = new ArrayList<>();

        String projection = "{_id:0," + SUPPLIER_NAME + ":1," + SUPPLIER_ADDRESS + ":1}";
        invoicesMongoCollection.find().projection(BasicDBObject.parse(projection)).iterator().forEachRemaining(e -> suppliers.add(gson.fromJson(e.toJson(), Supplier.class)));

        return suppliers;
    }


}
