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

    public void removeProduct(Product product) {
        productList.remove(product.getUuid());
        for (CartListener l : listeners) {
            l.cartChanged();
        }
    }

    public int getAmount(Product product) {
        if (contains(product)) {
            return amountList.get(productList.indexOf(product.getUuid()));
        } else return -1;
    }

    public boolean contains(Product product) {
        return productList.contains(product.getUuid());
    }
}
