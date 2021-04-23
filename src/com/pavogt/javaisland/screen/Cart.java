package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.LoginManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.CartListener;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.Product;
import com.pavogt.javaisland.data.ProductDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cart extends Panel implements CartListener {
    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final LoginManager loginManager;
    private final CartManager cartManager;

    private List productList;
    Label productName;
    Label productPrice;
    Label productDescription;
    Label productAmount;

    public Cart(ClientDataBase clientDB, ProductDataBase productDB, LoginManager loginManager, CartManager cartManager) {
        this.clientDB = clientDB;
        this.productDB = productDB;
        this.loginManager = loginManager;
        this.cartManager = cartManager;

        this.cartManager.addListener(this);

        makeScreen();
    }

    private void makeScreen() {
        productList = new List(100, false);

        for (Product product : productDB.getData()) {
            if (cartManager.contains(product))
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
        productDescription = new Label("Desc");
        productDescription.setBounds(480, 250, 140, 100);
        productAmount = new Label("Quantidade");
        productAmount.setBounds(510, 370, 80,30);
        Button amountLess = new Button("-");
        Button amountMore = new Button("+");

        add(productName);
        add(productPrice);
        add(productDescription);
        add(productAmount);

        add(productList);
        BackgroundPanel back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
        add(back);


        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    private void productSelected(AWTEvent e) {
        int index = productList.getSelectedIndex();
        if (index != -1) {
            Product c = productDB.getData().get(index);
            productName.setText(c.getName());
            productPrice.setText("R$ " + c.getPrice());
            productDescription.setText(c.getDescription());
            productAmount.setText("Qtd.: " + cartManager.getAmount(c));
        }
    }

    @Override
    public void cartChanged() {
        productList.removeAll();

        for (Product product : productDB.getData()) {
            if (cartManager.contains(product))
                productList.add(product.getName() + " - " + cartManager.getAmount(product));
        }
    }
}
