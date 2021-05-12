package com.pavogt.javaisland.data;

public class Product extends DataBaseItem {

    public static final long serialVersionUID = 2L;

    private final long uuid;
    private String name;
    private float price;
    private int quantity;
    private String description;

    public Product(DataBase<Product> db, long uuid, String name, float price, int quantity, String description) {
        super(db);
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
        notifyDataBase();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        notifyDataBase();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyDataBase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyDataBase();
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
