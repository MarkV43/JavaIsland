package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.component.Scrollable;
import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.Product;
import com.pavogt.javaisland.data.ProductDataBase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Store extends Panel {

    private ArrayList<Client> clients;
    private ArrayList<Product> products;

    Scrollable cart;
    BackgroundPanel back;

    private ClientDataBase clientDB;
    private ProductDataBase productDB;


    public Store(ClientDataBase clientDB, ProductDataBase productDB) {

        this.clientDB = clientDB;
        this.productDB = productDB;

        /*
        products     = new Product[100];
        products[0]  = new Product(0, "RTX 3080Ti", 12034.58f, 3, "");
        products[1]  = new Product(1, "RTX 3070Ti", 10422.66f, 12, "");
        products[2]  = new Product(2, "RTX 3060Ti", 8050.36f, 5, "");
        products[3]  = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[4]  = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[5]  = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[6]  = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");
        products[7]  = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[8]  = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[9]  = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[10] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");
        products[11] = new Product(1, "RTX 3070Ti", 10422.66f, 12, "");
        products[12] = new Product(2, "RTX 3060Ti", 8050.36f, 5, "");
        products[13] = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[14] = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[15] = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[16] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");
        products[17] = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[18] = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[19] = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[20] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");

        this.productDB.getData().addAll(Arrays.asList(products));*/
        clients = this.clientDB.getData();
        products = this.productDB.getData();

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

}
