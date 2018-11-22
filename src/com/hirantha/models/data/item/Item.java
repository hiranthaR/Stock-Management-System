package com.hirantha.models.data.item;

import java.util.Objects;

public class Item {

    private String _id; // == item code
    private String name;
    private String category;
    private String unit;
    private double receipt_price;
    private double marked_price;
    private double selling_price;
    private boolean percentage;
    private double rank1;
    private double rank2;
    private double rank3;

    public Item(String _id, String name, String category, String unit, double receipt_price, double marked_price, double selling_price, boolean percentage, double rank1, double rank2, double rank3) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.receipt_price = receipt_price;
        this.marked_price = marked_price;
        this.selling_price = selling_price;
        this.percentage = percentage;
        this.rank1 = rank1;
        this.rank2 = rank2;
        this.rank3 = rank3;
    }

    public String getItemCode() {
        return _id;
    }

    public void setItemCode(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getReceiptPrice() {
        return receipt_price;
    }

    public void setReceiptPrice(double receipt_price) {
        this.receipt_price = receipt_price;
    }

    public double getMarkedPrice() {
        return marked_price;
    }

    public void setMarkedPrice(double marked_price) {
        this.marked_price = marked_price;
    }

    public double getSellingPrice() {
        return selling_price;
    }

    public void setSellingPrice(double selling_price) {
        this.selling_price = selling_price;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public double getRank1() {
        return rank1;
    }

    public void setRank1(double rank1) {
        this.rank1 = rank1;
    }

    public double getRank2() {
        return rank2;
    }

    public void setRank2(double rank2) {
        this.rank2 = rank2;
    }

    public double getRank3() {
        return rank3;
    }

    public void setRank3(double rank3) {
        this.rank3 = rank3;
    }

    public double getDiscountAccoringToRank(int rank) {
        switch (rank) {
            case 1:
                return rank1;
            case 2:
                return rank2;
            case 3:
                return rank3;
            default:
                return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(_id, item._id);
    }
}
