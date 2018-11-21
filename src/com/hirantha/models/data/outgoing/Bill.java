package com.hirantha.models.data.outgoing;

import com.hirantha.models.data.item.BillTableItem;

import java.util.Date;
import java.util.List;

public class Bill {

    String _id;
    Date date;
    String customerId;
    String customerName;
    String customerAddress;
    int customerRank;

    List<BillTableItem> tableItems;
    double totalBillCost;
    String preparedAdminName;
    String preparedAdminId;
    String checkedAdminName;
    String checkedAdminId;
    String acceptedAdminName;
    String acceptedAdminId;
    String vehicleNumber;

    public Bill(String _id, Date date, String customerId, String customerName, String customerAddress, int customerRank, List<BillTableItem> tableItems, double totalBillCost, String preparedAdminName, String preparedAdminId, String checkedAdminName, String checkedAdminId, String acceptedAdminName, String acceptedAdminId, String vehicleNumber) {
        this._id = _id;
        this.date = date;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerRank = customerRank;
        this.tableItems = tableItems;
        this.totalBillCost = totalBillCost;
        this.preparedAdminName = preparedAdminName;
        this.preparedAdminId = preparedAdminId;
        this.checkedAdminName = checkedAdminName;
        this.checkedAdminId = checkedAdminId;
        this.acceptedAdminName = acceptedAdminName;
        this.acceptedAdminId = acceptedAdminId;
        this.vehicleNumber = vehicleNumber;
    }

    public String get_id() {
        return _id;
    }

    public Bill set_id(String _id) {
        this._id = _id;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Bill setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Bill setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Bill setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Bill setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        return this;
    }

    public int getCustomerRank() {
        return customerRank;
    }

    public Bill setCustomerRank(int customerRank) {
        this.customerRank = customerRank;
        return this;
    }

    public List<BillTableItem> getTableItems() {
        return tableItems;
    }

    public Bill setTableItems(List<BillTableItem> tableItems) {
        this.tableItems = tableItems;
        return this;
    }

    public double getTotalBillCost() {
        return totalBillCost;
    }

    public Bill setTotalBillCost(double totalBillCost) {
        this.totalBillCost = totalBillCost;
        return this;
    }

    public String getPreparedAdminName() {
        return preparedAdminName;
    }

    public Bill setPreparedAdminName(String preparedAdminName) {
        this.preparedAdminName = preparedAdminName;
        return this;
    }

    public String getPreparedAdminId() {
        return preparedAdminId;
    }

    public Bill setPreparedAdminId(String preparedAdminId) {
        this.preparedAdminId = preparedAdminId;
        return this;
    }

    public String getCheckedAdminName() {
        return checkedAdminName;
    }

    public Bill setCheckedAdminName(String checkedAdminName) {
        this.checkedAdminName = checkedAdminName;
        return this;
    }

    public String getCheckedAdminId() {
        return checkedAdminId;
    }

    public Bill setCheckedAdminId(String checkedAdminId) {
        this.checkedAdminId = checkedAdminId;
        return this;
    }

    public String getAcceptedAdminName() {
        return acceptedAdminName;
    }

    public Bill setAcceptedAdminName(String acceptedAdminName) {
        this.acceptedAdminName = acceptedAdminName;
        return this;
    }

    public String getAcceptedAdminId() {
        return acceptedAdminId;
    }

    public Bill setAcceptedAdminId(String acceptedAdminId) {
        this.acceptedAdminId = acceptedAdminId;
        return this;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Bill setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        return this;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "_id='" + _id + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerRank=" + customerRank +
                ", tableItems=" + tableItems +
                ", totalBillCost=" + totalBillCost +
                ", preparedAdminName='" + preparedAdminName + '\'' +
                ", preparedAdminId='" + preparedAdminId + '\'' +
                ", checkedAdminName='" + checkedAdminName + '\'' +
                ", checkedAdminId='" + checkedAdminId + '\'' +
                ", acceptedAdminName='" + acceptedAdminName + '\'' +
                ", acceptedAdminId='" + acceptedAdminId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                '}';
    }
}
