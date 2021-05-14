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
    TextArea productDescription;

    Font font1 = new Font("Rockwell Nova", Font.PLAIN, 25);
    Font font2 = new Font("Rockwell Nova", Font.PLAIN, 18);

    private final ProductDataBase productDB;
    private final CartManager cartManager;


    public Store(ClientDataBase clientDB, ProductDataBase productDB, CartManager cartManager) {
        this.productDB = productDB;
        this.cartManager = cartManager;

        productDB.addListener(this::productDataBaseChanged);

        products = productDB.getData();

        setupScreen();
    }

    void setupScreen() {
        productList = new List(100, false);

        for (Product product : products) {
            productList.add(product.getName() + " - R$ " + product.getPrice());
        }

        productList.setBounds(20, 60, 340, 580);

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
        productName.setBounds(480, 60, 300, 30);
        productName.setFont(font2);
        productPrice = new Label("Price");
        productPrice.setBounds(480, 110, 300, 30);
        productPrice.setFont(font2);
        productQuantity = new Label("Qtd");
        productQuantity.setBounds(480, 160, 300, 30);
        productQuantity.setFont(font2);
        productDescription = new TextArea("",6,100, TextArea.SCROLLBARS_NONE);
        productDescription.setBounds(480, 210, 300, 200);
        productDescription.setFont(font2);

        Label productslist = new Label("PRODUCTS:");
        productslist.setFont(font1);
        productslist.setBounds(110,20,200,30);


        add(productName);
        add(productPrice);
        add(productQuantity);
        add(productDescription);
        add(productslist);

        Button addToCart = new Button("Add to Cart");
        addToCart.setFont(font2);
        addToCart.setBounds(560, 460, 140, 30);
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
        setBackground(Color.LIGHT_GRAY);


        setLayout(null);
        setBounds(0, 90, 1280, 660);
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
