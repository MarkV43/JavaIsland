package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.component.Scrollable;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.DataBaseListener;
import com.pavogt.javaisland.data.Product;
import com.pavogt.javaisland.data.ProductDataBase;

import java.awt.*;
import java.util.ArrayList;


public class Store extends Panel {

    List cart;
    ArrayList<Product> products;
    BackgroundPanel back;

    private ClientDataBase clientDB;
    private ProductDataBase productDB;

    public Store(ClientDataBase clientDB, ProductDataBase productDB) {

        this.clientDB = clientDB;
        this.productDB = productDB;

        clientDB.addListener(this::clientDataBaseChanged);
        productDB.addListener(this::productDataBaseChanged);

        products = productDB.getData();

        cart = new List(100, false);

        for (Product product : products) {
            cart.add(product.getName() + " - R$ " + product.getPrice());
        }

        cart.setBounds(100, 50, 500, 500);

        add(cart);
        back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
        add(back);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    public void clientDataBaseChanged() {

    }

    public void productDataBaseChanged() {
        cart.removeAll();
        for (Product product : products) {
            cart.add(product.getName() + " - R$ " + product.getPrice());
        }
    }
}
