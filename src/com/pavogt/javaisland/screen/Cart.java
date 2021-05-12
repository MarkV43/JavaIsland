package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.LoginManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cart extends Panel implements CartListener, LoginListener {
    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final LoginManager loginManager;
    private final CartManager cartManager;
    TextArea productDescription;

    Font font1 = new Font("Rockwell Nova", Font.PLAIN, 25);
    Font font2 = new Font("Rockwell Nova", Font.PLAIN, 18);

    private List productList;
    Label productName;
    Label productPrice;
    Label productAmount;

    TextField username;
    JPasswordField password;
    Button login;
    Label welcome;
    Button removeItem;
    Label totalPrice;
    Button finishBuying;

    long priceTot;

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

        productList.setBounds(20, 60, 340, 540);

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

        finishBuying = new Button("Finalizar Compra");
        finishBuying.setBounds(20, 610, 340, 30);
        finishBuying.addActionListener(e -> {
            finishPurchase();
        });
        finishBuying.setEnabled(false);
        add(finishBuying);

        productName = new Label("Name");
        productName.setBounds(480, 60, 140, 30);
        productName.setFont(font2);
        productPrice = new Label("Price");
        productPrice.setBounds(480, 110, 140, 30);
        productPrice.setFont(font2);
        productDescription = new TextArea("", 6, 100, TextArea.SCROLLBARS_NONE);
        productDescription.setBounds(480, 160, 300, 200);
        productDescription.setFont(font2);
        productAmount = new Label("Qtd");
        productAmount.setBounds(585, 370, 80, 30);
        productAmount.setFont(font2);
        totalPrice = new Label("Price");
        totalPrice.setBounds(480, 470, 140, 30);

        removeItem = new Button("Remove item");
        removeItem.setBounds(480, 410, 300, 30);
        removeItem.setFont(font2);
        removeItem.addActionListener(e -> {
            cartManager.removeProduct(selectedProduct);
            selectedProduct = null;
        });

        Label productslist = new Label("ITEMS:");
        productslist.setFont(font1);
        productslist.setBounds(140, 20, 200, 30);


        Button amountLess = new Button("-");
        Button amountMore = new Button("+");
        amountLess.setBounds(555, 370, 30, 30);
        amountMore.setBounds(665, 370, 30, 30);

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
        add(removeItem);
        add(productList);
        add(productslist);
        add(totalPrice);


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
        back.setBounds(0, 0, 1280, 720);

        setBackground(Color.LIGHT_GRAY);
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

    void finishPurchase() {
        Client c = loginManager.getLoggedUser();
        if (c.getBalance() >= priceTot && priceTot > 0 && cartManager.getProductList().size() > 0) {
            boolean enabled = true;
            for (Long prodId : cartManager.getProductList()) {
                Product prod = productDB.getFromUuid(prodId);
                if (cartManager.getAmount(prodId) > prod.getQuantity()) {
                    enabled = false;
                }
            }

            if (enabled) {
                c.setBalance(c.getBalance() - priceTot);

                Transaction trans = new Transaction(priceTot, cartManager.getProductList(), cartManager.getAmountList());

                c.addToHistory(trans);

                cartManager.removeAll();

                selectedProduct = null;

                productSelected(null);
            }
        }
    }

    @Override
    public void cartChanged() {
        productList.removeAll();

        priceTot = 0;
        for (long uuid : cartManager.getProductList()) {
            Product product = productDB.getFromUuid(uuid);

            productList.add(product.getName() + " - R$ " + product.getPrice() + " - " + cartManager.getAmount(uuid));
            priceTot += product.getPrice() * cartManager.getAmount(uuid);
        }

        totalPrice.setText("Total: R$ " + String.format("%.2f", priceTot / 100f));

        if (selectedProduct != null) {
            productList.select(cartManager.indexOf(selectedProduct));
        }
        productSelected(null);
    }

    @Override
    public void loginChanged() {
        System.out.println(loginManager.isLoggedIn());
        if (loginManager.isLoggedIn()) {
            username.setVisible(false);
            password.setVisible(false);
            login.setVisible(false);
            welcome.setVisible(true);
            welcome.setText("Bem vindo, " + loginManager.getLoggedUser().getName() + ". Você tem R$ " + loginManager.getLoggedUser().getBalance());
            finishBuying.setEnabled(true);

        } else {
            username.setVisible(true);
            password.setVisible(true);
            login.setVisible(true);
            welcome.setVisible(false);
            finishBuying.setEnabled(false);
        }
    }
}
