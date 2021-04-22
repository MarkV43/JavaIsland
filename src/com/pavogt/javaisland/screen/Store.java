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

    Scrollable cart;
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

        cart = new Scrollable(new GridLayout(0, 1, 5, 5));
        int count = 0;

        for (Product product : products) {
            if (product == null) break;
            Panel p = new Panel();
            p.add(new Label(product.getName()));
            p.add(new Label("R$ " + product.getPrice()));
            p.setBackground(Color.RED);
            cart.getPanel().add(p);
            count++;
        }

        GridLayout gl = ((GridLayout) cart.getPanel().getLayout());
        gl.setRows(count);
        gl.layoutContainer(cart.getPanel());

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
        System.out.println("Estive aqui!");
        cart.removeAll();
        for (Product stk : productDB.getData()) {
            cart.add(stk.getName());

        }
    }
}
