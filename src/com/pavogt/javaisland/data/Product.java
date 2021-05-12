package com.pavogt.javaisland.data;

public class Product extends DataBaseItem<Product> {

    public static final long serialVersionUID = 3L;

    private final long uuid;
    private String name;
    private long price;
    private int quantity;
    private String description;

    public Product(DataBase<Product> db, long uuid, String name, long price, int quantity, String description) {
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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
