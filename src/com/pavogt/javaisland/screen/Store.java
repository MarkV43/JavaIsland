package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.CartManager;
import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.Product;
import com.pavogt.javaisland.data.ProductDataBase;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Normalizer;
import java.util.ArrayList;



public class Store extends Panel implements KeyListener {

    ArrayList<Product> productList;
    Product selectedProduct;
    private TextArea search;

    List list;
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

        productList = new ArrayList<>(productDB.getData());

        setupScreen();
    }

    void setupScreen() {
        list = new List(100, false);

        search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(20, 60, 340, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        search.addKeyListener(this);
        add(search);

        updateProductList();

        list.setBounds(20, 100, 340, 540);

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

        productName = new Label("Name");
        productName.setBounds(480, 60, 420, 30);
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
            int index = list.getSelectedIndex();
            if (index != -1) {
                cartManager.addProduct(selectedProduct, 1);
            }
        });
        add(addToCart);

        add(list);
        back = new BackgroundPanel();
        back.setBounds(0,0,1280,720);
        setBackground(Color.LIGHT_GRAY);


        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    void updateProductList(){
        list.removeAll();
        for (Product product : productList) {
            long p = product.getPrice();
            String decp = String.valueOf(p % 100);
            if (p % 100 < 10)
                decp = '0' + decp;
            list.add(product.getName() + " - R$ " + String.valueOf(p / 100) + '.' + decp);
        }
    }

    public void productDataBaseChanged() {
        makeSearch(true);
    }

    public void productSelected(AWTEvent e) {
        int index = list.getSelectedIndex();
        if (index != -1) {
            selectedProduct = productList.get(index);
            long bal = selectedProduct.getPrice();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            productPrice.setText("R$ " + String.valueOf(bal / 100) + '.' + dec);
            productName.setText(selectedProduct.getName());
            productQuantity.setText("Estoque: " + selectedProduct.getQuantity());
            productDescription.setText(selectedProduct.getDescription());
        }
    }

    private String removeAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    String previousSearch = null;

    private void makeSearch(boolean update) {
        String text = removeAccents(search.getText().toLowerCase());
        if (text.equals(previousSearch) && !update) {
            return;
        }
        previousSearch = text;
        String[] parts = text.split(" ");
        ArrayList<Product> products = productDB.getData();
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
        updateProductList();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        makeSearch(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        makeSearch(false);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        makeSearch(false);
    }
}
