package com.pavogt.javaisland.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {

    public static final long serialVersionUID = 1L;

    private float price;

    private ArrayList<Long> products;
    private ArrayList<Integer> amounts;

    public Transaction(float price, ArrayList<Long> products, ArrayList<Integer> amounts) {
        this.price = price;
        this.products = products;
        this.amounts = amounts;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
