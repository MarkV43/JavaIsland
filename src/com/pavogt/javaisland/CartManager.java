package com.pavogt.javaisland;

import com.pavogt.javaisland.data.CartListener;
import com.pavogt.javaisland.data.Product;

import java.util.ArrayList;

public class CartManager {
    ArrayList<Long> productList;
    ArrayList<Integer> amountList;
    ArrayList<CartListener> listeners;

    public CartManager() {
        productList = new ArrayList<>();
        amountList = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public int getSize(){
        return productList.size();
    }

    public void addListener(CartListener listener) {
        listeners.add(listener);
    }

    public void addProduct(Product product, int amount) {
        if (!productList.contains(product.getUuid())) {
            productList.add(product.getUuid());
            amountList.add(amount);
            for (CartListener l : listeners) {
                l.cartChanged();
            }
        }
    }

    public void changeAmount(Product product, int newAmount) {
        if (productList.contains(product.getUuid())) {
            amountList.set(productList.indexOf(product.getUuid()), newAmount);
            for (CartListener l : listeners) {
                l.cartChanged();
            }
        }
    }

    public void addAmount(Product product, int change) {
        changeAmount(product, change + getAmount(product));
    }

    public void removeProduct(Product product) {
        productList.remove(product.getUuid());
        for (CartListener l : listeners) {
            l.cartChanged();
        }
    }

    public long get(int index) {
        return productList.get(index);
    }

    public int getAmount(Product product) {
        if (contains(product)) {
            return amountList.get(productList.indexOf(product.getUuid()));
        } else return -1;
    }

    public int getAmount(int index) {
        return amountList.get(index);
    }

    public int getAmount(long uuid) {
        return amountList.get(productList.indexOf(uuid));
    }

    public boolean contains(Product product) {
        return productList.contains(product.getUuid());
    }

    public int indexOf(Product product) {
        return productList.indexOf(product.getUuid());
    }

    public ArrayList<Long> getProductList() {
        return productList;
    }

    public ArrayList<Integer> getAmountList() {
        return amountList;
    }
}
