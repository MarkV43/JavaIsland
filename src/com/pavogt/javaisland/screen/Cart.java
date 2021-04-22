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
    TextArea productDescription;
    Label productAmount;
    Button removeItem;

    Font font1 = new Font("Rockwell Nova", Font.PLAIN, 25);
    Font font2 = new Font("Rockwell Nova", Font.PLAIN, 18);

    private Product selectedProduct;

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

        for (long uuid : cartManager.getProductList()) {
            Product product = productDB.getFromUuid(uuid);

            productList.add(product.getName() + " - R$ " + product.getPrice() + " - " + cartManager.getAmount(uuid));
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
        productName.setBounds(480, 60, 140, 30);
        productName.setFont(font2);
        productPrice = new Label("Price");
        productPrice.setBounds(480, 110, 140, 30);
        productPrice.setFont(font2);
        productDescription = new TextArea("",6,100, TextArea.SCROLLBARS_NONE);
        productDescription.setBounds(480, 160, 300, 200);
        productDescription.setFont(font2);
        productAmount = new Label("Qtd");
        productAmount.setBounds(585, 370, 80,30);
        productAmount.setFont(font2);

        removeItem = new Button("Remove item");
        removeItem.setBounds(480,410,300,30);
        removeItem.setFont(font2);

        Label productslist = new Label("ITEMS:");
        productslist.setFont(font1);
        productslist.setBounds(140,20,200,30);


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
        BackgroundPanel back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
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
}
