package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.Product;
import com.pavogt.javaisland.data.ProductDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Store extends Panel {

    List productList;
    ArrayList<Product> products;
    BackgroundPanel back;

    Label productName;
    Label productPrice;
    Label productQuantity;
    Label productDescription;

    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final CartManager cartManager;


    public Store(ClientDataBase clientDB, ProductDataBase productDB, CartManager cartManager) {
        this.clientDB = clientDB;
        this.productDB = productDB;
        this.cartManager = cartManager;

        clientDB.addListener(this::clientDataBaseChanged);
        productDB.addListener(this::productDataBaseChanged);

        products = productDB.getData();

        setupScreen();
    }

    void setupScreen() {
        productList = new List(100, false);

        for (Product product : products) {
            productList.add(product.getName() + " - R$ " + product.getPrice());
        }

        productList.setBounds(100, 50, 300, 500);

        productList.addActionListener(this::productSelected);
        productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productSelected(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                productSelected(e);
            }
        });

        productName = new Label("Name");
        productName.setBounds(480, 100, 140, 30);
        productPrice = new Label("Price");
        productPrice.setBounds(480, 150, 140, 30);
        productQuantity = new Label("Qtd");
        productQuantity.setBounds(480, 200, 140, 30);
        productDescription = new Label("Desc");
        productDescription.setBounds(480, 250, 140, 100);

        add(productName);
        add(productPrice);
        add(productQuantity);
        add(productDescription);

        Button addToCart = new Button("Add to Cart");
        addToCart.setBounds(480, 370, 140, 30);
        addToCart.addActionListener(e -> {
            int index = productList.getSelectedIndex();
            if (index != -1) {
                cartManager.addProduct(productDB.getData().get(index), 1);
            }
        });
        add(addToCart);

        add(productList);
        back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
        add(back);


        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    public void clientDataBaseChanged() {

    }

    public void productDataBaseChanged() {
        productList.removeAll();
        for (Product product : products) {
            productList.add(product.getName() + " - R$ " + product.getPrice());
        }
    }

    public void productSelected(AWTEvent e) {
        int index = productList.getSelectedIndex();
        if (index != -1) {
            Product c = productDB.getData().get(index);
            productName.setText(c.getName());
            productPrice.setText("R$ " + c.getPrice());
            productQuantity.setText("Estoque: " + c.getQuantity());
            productDescription.setText(c.getDescription());
        }
    }
}
