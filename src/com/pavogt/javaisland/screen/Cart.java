package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.LoginManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Normalizer;
import java.util.ArrayList;

public class Cart extends Panel implements CartListener, LoginListener, KeyListener {
    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final LoginManager loginManager;
    private final CartManager cartManager;
    private TextArea search;
    TextArea productDescription;

    Font font1 = new Font("Rockwell Nova", Font.PLAIN, 25);
    Font font2 = new Font("Rockwell Nova", Font.PLAIN, 18);

    private List list;
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

    private ArrayList<Product> productList;
    private Product selectedProduct;

    public Cart(ClientDataBase clientDB, ProductDataBase productDB, LoginManager loginManager, CartManager cartManager) {
        this.clientDB = clientDB;
        this.productDB = productDB;
        this.loginManager = loginManager;
        this.cartManager = cartManager;

        this.cartManager.addListener(this);
        this.loginManager.addListener(this);

        this.productList = new ArrayList<>();
        for (Long uuid : cartManager.getProductList()) {
            productList.add(productDB.getFromUuid(uuid));
        }

        makeScreen();
    }

    private void makeScreen() {
        list = new List(100, false);

        search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(20, 60, 340, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        search.addKeyListener(this);
        add(search);

        for (Product product : productList) {
            long bal = product.getPrice();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            list.add(product.getName() + " - R$ " + String.valueOf(bal / 100) + '.' + dec + " - " + cartManager.getAmount(product));
        }

        list.setBounds(20, 100, 340, 500);

        list.addActionListener(this::productSelected);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productSelected(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                productSelected(e);
            }
        });

        add(list);

        finishBuying = new Button("Finalizar Compra");
        finishBuying.setBounds(20, 610, 340, 30);
        finishBuying.addActionListener(e -> {
            finishPurchase();
        });
        finishBuying.setEnabled(false);
        add(finishBuying);

        productName = new Label("Name");
        productName.setBounds(480, 60, 420, 30);
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
        add(list);
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
        int index = list.getSelectedIndex();
        if (index != -1) {
            Product c = productDB.getFromUuid(cartManager.get(index));
            long bal = c.getPrice();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            productPrice.setText("R$ " + String.valueOf(bal / 100) + '.' + dec);
            selectedProduct = c;
            productName.setText(c.getName());
            productDescription.setText(c.getDescription());
            productAmount.setText("Qtd: " + cartManager.getAmount(index));
        }
    }

    void finishPurchase() {
        Client c = loginManager.getLoggedUser();
        if (c.getBalance() >= priceTot && priceTot > 0 && productList.size() > 0) {
            boolean enabled = true;
            for (Product prod : productList) {
                if (cartManager.getAmount(prod.getUuid()) > prod.getQuantity()) {
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
    void updateCartList(){
        list.removeAll();

        priceTot = 0;
        for (Product product : productList) {
            long bal = product.getPrice();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            int amount = cartManager.getAmount(product);
            list.add(product.getName() + " - R$ " + bal / 100 + '.' + dec + " - " + amount);
            priceTot += product.getPrice() * amount;
        }
    }

    @Override
    public void cartChanged() {
        makeSearch();

        totalPrice.setText("Total: R$ " + String.format("%.2f", priceTot / 100f));

        if (selectedProduct != null) {
            list.select(cartManager.indexOf(selectedProduct));
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
            long bal = loginManager.getLoggedUser().getBalance();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            welcome.setText("Bem vindo, " + loginManager.getLoggedUser().getName() + ". Você tem R$ " + bal / 100 + '.' + dec);
            finishBuying.setEnabled(true);

        } else {
            username.setVisible(true);
            password.setVisible(true);
            login.setVisible(true);
            welcome.setVisible(false);
            finishBuying.setEnabled(false);
        }
    }

    private String removeAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private void makeSearch() {
        String text = removeAccents(search.getText().toLowerCase());
        String[] parts = text.split(" ");
        ArrayList<Product> products = new ArrayList<>();
        for (long uuid: cartManager.getProductList()) {
            products.add(productDB.getFromUuid(uuid));
        }
        productList.clear();
        if (text.trim().length() == 0) {
            productList.addAll(products);
        } else {
            for (Product product : products) {
                String name = removeAccents(product.getName().toLowerCase());
                boolean show = true;
                for (String part : parts) {
                    if (!name.contains(part)) {
                        show = false;
                        break;
                    }
                }
                if (show) {
                    productList.add(product);
                }
            }
        }
        updateCartList();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        makeSearch();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        makeSearch();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        makeSearch();
    }
}
