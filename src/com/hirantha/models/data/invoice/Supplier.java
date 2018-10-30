package com.hirantha.models.data.invoice;

public class Supplier {

    private String supplier_name;
    private String supplier_address;

    public Supplier(String name, String address) {
        this.supplier_name = name;
        this.supplier_address = address;
    }

    public String getName() {
        return supplier_name;
    }

    public Supplier setName(String name) {
        this.supplier_name = name;
        return this;
    }

    public String getAddress() {
        return supplier_address;
    }

    public Supplier setAddress(String address) {
        this.supplier_address = address;
        return this;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + supplier_name + '\'' +
                ", address='" + supplier_address + '\'' +
                '}';
    }
}
