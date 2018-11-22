package com.hirantha.models.data.item;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class InvoiceTableItem {

    private SimpleStringProperty itemId; // == item code
    private SimpleStringProperty name;
    private SimpleStringProperty unit;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty costPerItem;

    public InvoiceTableItem(String itemId, String name, String unit, int quantity, double costPerItem) {
        this.itemId = new SimpleStringProperty(itemId);
        this.name = new SimpleStringProperty(name);
        this.unit = new SimpleStringProperty(unit);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.costPerItem = new SimpleDoubleProperty(costPerItem);
    }

    public String getItemId() {
        return itemId.get();
    }

    public SimpleStringProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getCostPerItem() {
        return costPerItem.get();
    }

    public SimpleDoubleProperty costPerItemProperty() {
        return costPerItem;
    }

    public void setCostPerItem(double costPerItem) {
        this.costPerItem.set(costPerItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceTableItem invoiceTableItem = (InvoiceTableItem) o;
        return Objects.equals(itemId, invoiceTableItem.itemId);
    }

}
