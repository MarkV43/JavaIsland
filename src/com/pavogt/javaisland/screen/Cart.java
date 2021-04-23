package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.LoginManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;

public class Cart extends Panel implements CartListener, LoginListener {
    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final LoginManager loginManager;
    private final CartManager cartManager;

    private List productList;
    Label productName;
    Label productPrice;
    Label productDescription;
    Label productAmount;

    TextField username;
    JPasswordField password;
    Button login;
    Label welcome;

    private Product selectedProduct;

    public Cart(ClientDataBase clientDB, ProductDataBase productDB, LoginManager loginManager, CartManager cartManager) {
        this.clientDB = clientDB;
        this.productDB = productDB;
        this.loginManager = loginManager;
        this.cartManager = cartManager;

        this.cartManager.addListener(this);
        this.loginManager.addListener(this);

        makeScreen();
    }

    private void makeScreen() {
        productList = new List(100, false);

        for (long uuid : cartManager.getProductList()) {
            Product product = productDB.getFromUuid(uuid);

            productList.add(product.getName() + " - R$ " + product.getPrice() + " - " + cartManager.getAmount(uuid));
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

        add(productList);

        productName = new Label("Name");
        productName.setBounds(480, 100, 140, 30);
        productPrice = new Label("Price");
        productPrice.setBounds(480, 150, 140, 30);
        productDescription = new Label("Desc");
        productDescription.setBounds(480, 200, 140, 100);
        productAmount = new Label("Qtd");
        productAmount.setBounds(510, 320, 80,30);
        Button amountLess = new Button("-");
        Button amountMore = new Button("+");
        amountLess.setBounds(480, 320, 30, 30);
        amountMore.setBounds(590, 320, 30, 30);

        amountLess.addActionListener(e -> {
            int amount = Math.max(cartManager.getAmount(selectedProduct) - 1, 1);
            cartManager.changeAmount(selectedProduct, amount);
        });

        amountMore.addActionListener(e -> {
            int max = selectedProduct.getQuantity();
            int amount = Math.min(cartManager.getAmount(selectedProduct) + 1, max);
            cartManager.changeAmount(selectedProduct, amount);
        });

        add(productName);
        add(productPrice);
        add(productDescription);
        add(productAmount);
        add(amountLess);
        add(amountMore);


        username = new TextField();
        password = new JPasswordField();
        login = new Button("Login");

        username.setBounds(900, 20, 166, 30);
        password.setBounds(1086, 20, 106, 30);
        login.setBounds(1212, 20, 50, 30);

        login.addActionListener(e -> {
            loginManager.login(username.getText(), new String(password.getPassword()));
        });

        add(username);
        add(password);
        add(login);

        welcome = new Label("Bem vindo, <insira o nome aqui>", Label.RIGHT);
        welcome.setBounds(960, 20, 300, 30);

        welcome.setVisible(false);
        add(welcome);

        BackgroundPanel back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
        add(back);


        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    private void productSelected(AWTEvent e) {
        int index = productList.getSelectedIndex();
        if (index != -1) {
            Product c = productDB.getFromUuid(cartManager.get(index));
            selectedProduct = c;
            productName.setText(c.getName());
            productPrice.setText("R$ " + c.getPrice());
            productDescription.setText(c.getDescription());
            productAmount.setText("Qtd: " + cartManager.getAmount(index));
        }
    }

    @Override
    public void cartChanged() {
        productList.removeAll();

        for (long uuid : cartManager.getProductList()) {
            Product product = productDB.getFromUuid(uuid);

            productList.add(product.getName() + " - R$ " + product.getPrice() + " - " + cartManager.getAmount(uuid));
        }

        if (selectedProduct != null)
            productList.select(cartManager.indexOf(selectedProduct));
        productSelected(null);
    }

    @Override
    public void loginChanged() {
        System.out.println("HAHAHA");
        if (loginManager.isLoggedIn()) {
            username.setVisible(false);
            password.setVisible(false);
            login.setVisible(false);
            welcome.setVisible(true);
            welcome.setText("Bem vindo, " + loginManager.getLoggedUser().getName());
        } else {
            username.setVisible(true);
            password.setVisible(true);
            login.setVisible(true);
            welcome.setVisible(false);
        }
    }
}
