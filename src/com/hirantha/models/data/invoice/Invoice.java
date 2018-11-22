package com.hirantha.models.data.invoice;

import com.hirantha.models.data.item.InvoiceTableItem;

import java.util.Date;
import java.util.List;

public class Invoice {

    String _id;
    Date date;
    String invoiceNumber;
    String name;
    String address;

    List<InvoiceTableItem> invoiceTableItems;
    double billCost;
    boolean cash;
    String bank;
    String branch;
    Date chequeDate;
    double amount;
    String preparedAdminName;
    String preparedAdminId;
    String checkedAdminName;
    String checkedAdminId;
    String acceptedAdminName;
    String acceptedAdminId;

    public Invoice(String _id, Date date, String invoiceNumber, String name, String address, List<InvoiceTableItem> invoiceTableItems, double billCost, boolean cash, String bank, String branch, Date chequeDate, double amount, String preparedAdminName, String preparedAdminId, String checkedAdminName, String checkedAdminId, String acceptedAdminName, String acceptedAdminId) {
        this._id = _id;
        this.date = date;
        this.invoiceNumber = invoiceNumber;
        this.name = name;
        this.address = address;
        this.invoiceTableItems = invoiceTableItems;
        this.billCost = billCost;
        this.cash = cash;
        this.bank = bank;
        this.branch = branch;
        this.chequeDate = chequeDate;
        this.amount = amount;
        this.preparedAdminName = preparedAdminName;
        this.preparedAdminId = preparedAdminId;
        this.checkedAdminName = checkedAdminName;
        this.checkedAdminId = checkedAdminId;
        this.acceptedAdminName = acceptedAdminName;
        this.acceptedAdminId = acceptedAdminId;
    }

    public String get_id() {
        return _id;
    }

    public Invoice set_id(String _id) {
        this._id = _id;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Invoice setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Invoice setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public Invoice setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Invoice setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<InvoiceTableItem> getInvoiceTableItems() {
        return invoiceTableItems;
    }

    public Invoice setInvoiceTableItems(List<InvoiceTableItem> invoiceTableItems) {
        this.invoiceTableItems = invoiceTableItems;
        return this;
    }

    public double getBillCost() {
        return billCost;
    }

    public Invoice setBillCost(double billCost) {
        this.billCost = billCost;
        return this;
    }

    public boolean isCash() {
        return cash;
    }

    public Invoice setCash(boolean cash) {
        this.cash = cash;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public Invoice setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public Invoice setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public Invoice setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Invoice setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getPreparedAdminName() {
        return preparedAdminName;
    }

    public Invoice setPreparedAdminName(String preparedAdminName) {
        this.preparedAdminName = preparedAdminName;
        return this;
    }

    public String getPreparedAdminId() {
        return preparedAdminId;
    }

    public Invoice setPreparedAdminId(String preparedAdminId) {
        this.preparedAdminId = preparedAdminId;
        return this;
    }

    public String getCheckedAdminName() {
        return checkedAdminName;
    }

    public Invoice setCheckedAdminName(String checkedAdminName) {
        this.checkedAdminName = checkedAdminName;
        return this;
    }

    public String getCheckedAdminId() {
        return checkedAdminId;
    }

    public Invoice setCheckedAdminId(String checkedAdminId) {
        this.checkedAdminId = checkedAdminId;
        return this;
    }

    public String getAcceptedAdminName() {
        return acceptedAdminName;
    }

    public Invoice setAcceptedAdminName(String acceptedAdminName) {
        this.acceptedAdminName = acceptedAdminName;
        return this;
    }

    public String getAcceptedAdminId() {
        return acceptedAdminId;
    }

    public Invoice setAcceptedAdminId(String acceptedAdminId) {
        this.acceptedAdminId = acceptedAdminId;
        return this;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "_id='" + _id + '\'' +
                ", date=" + date +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", invoiceTableItems=" + invoiceTableItems +
                ", billCost=" + billCost +
                ", cash=" + cash +
                ", bank='" + bank + '\'' +
                ", branch='" + branch + '\'' +
                ", chequeDate=" + chequeDate +
                ", amount=" + amount +
                ", preparedAdminName='" + preparedAdminName + '\'' +
                ", preparedAdminId=" + preparedAdminId +
                ", checkedAdminName='" + checkedAdminName + '\'' +
                ", checkedAdminId=" + checkedAdminId +
                ", acceptedAdminName='" + acceptedAdminName + '\'' +
                ", acceptedAdminId=" + acceptedAdminId +
                '}';
    }
}
