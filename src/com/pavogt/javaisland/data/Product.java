package com.pavogt.javaisland.data;

public class Product implements DataBaseItem {

    public static final long serialVersionUID = 3L;

    private final long uuid;
    private String name;
    private int price;
    private int quantity;
    private String description;

    public Product(long uuid, String name, int price, int quantity, String description) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public long getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
