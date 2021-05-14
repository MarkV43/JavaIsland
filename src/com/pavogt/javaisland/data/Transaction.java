package com.pavogt.javaisland.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {

    public static final long serialVersionUID = 1L;

    private long price;

    private ArrayList<Long> products;
    private ArrayList<Integer> amounts;

    public Transaction(long price, ArrayList<Long> products, ArrayList<Integer> amounts) {
        this.price = price;
        this.products = new ArrayList<>();
        this.amounts = new ArrayList<>();
        this.products.addAll(products);
        this.amounts.addAll(amounts);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public ArrayList<Long> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Long> products) {
        this.products = products;
    }

    public ArrayList<Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(ArrayList<Integer> amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "price=" + price +
                '}';
    }
}
