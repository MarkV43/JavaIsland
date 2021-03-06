package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;

public class Admin extends Panel implements DataBaseListener, KeyListener {

    private List stock;
    private TextArea search;
    private TextArea name;
    private TextArea price;
    private TextArea quantity;
    private TextArea description;
    private Label add;
    BackgroundPanel back;
    private ProductDataBase productDB;
    private Product selectedProduct;

    private ArrayList<Product> productList;

    public Admin(ProductDataBase productDB) {

        this.productDB = productDB;
        this.productDB.addListener(this);

        productList = new ArrayList<>(this.productDB.getData());

        Button bAdd = new Button("+");
        bAdd.setBounds(770, 120, 30, 30);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 18);
        bAdd.setFont(font);
        bAdd.addActionListener(e -> {
            Product h = selectedProduct;
            if (h != null) {
                quantity.setText(Integer.toString(Integer.parseInt(quantity.getText()) + 1));
            }
        });
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(810, 120, 30, 30);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        bRem.addActionListener(e -> {
            Product h = selectedProduct;
            if (h != null) {
                quantity.setText(Integer.toString(Integer.parseInt(quantity.getText()) - 1));
            }
        });
        add(bRem);

        search = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        search.setBounds(20, 20, 340, 30);
        search.setFont(font2);
        search.addKeyListener(this);
        add(search);

        TextArea name2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        name2.setBounds(925, 150, 288, 30);
        name2.setFont(font2);
        add(name2);

        TextArea quantity2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        quantity2.setBounds(925, 250, 288, 30);
        quantity2.setFont(font2);
        add(quantity2);

        TextArea price2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        price2.setBounds(925, 350, 288, 30);
        price2.setFont(font2);
        add(price2);

        stock = new List(100, false);

        for (Product p : productList) {
            stock.add(p.getName());
        }

        stock.setBounds(20, 60, 340, 580);
        add(stock);

        setBackground(Color.white);

        stock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemClicked();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                itemClicked();
            }
        });
        stock.addActionListener(e -> itemClicked());
        add(stock);

        name = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        name.setBounds(460, 20, 300, 30);
        name.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name);

        price = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        price.setBounds(460, 70, 300, 30);
        price.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(price);

        quantity = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        quantity.setBounds(460, 120, 300, 30);
        quantity.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantity);

        description = new TextArea("", 6, 100, TextArea.SCROLLBARS_NONE);
        description.setBounds(460, 170, 300, 150);
        description.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(description);

        add = new Label("Add product:");
        add.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add.setBounds(1015, 50, 150, 30);
        add(add);

        Label namelabel = new Label("Name:");
        namelabel.setBounds(1040, 100, 120, 30);
        namelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel);

        Label pricelabel = new Label("Price:");
        pricelabel.setBounds(1040, 300, 120, 30);
        pricelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(pricelabel);

        Label quantitylabel = new Label("Quantity:");
        quantitylabel.setBounds(1025, 200, 140, 30);
        quantitylabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantitylabel);

        Label namelabel2 = new Label("Name:");
        namelabel2.setBounds(380, 20, 140, 30);
        namelabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel2);

        Label pricelabel2 = new Label("Price: R$");
        pricelabel2.setBounds(380, 70, 140, 30);
        pricelabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(pricelabel2);

        Label quantitylabel2 = new Label("Quantity:");
        quantitylabel2.setBounds(380, 120, 140, 30);
        quantitylabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantitylabel2);

        Label description2 = new Label("Info:");
        description2.setBounds(380, 170, 140, 30);
        description2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(description2);


        Button newproduct = new Button("Add product");
        newproduct.setBounds(925, 400, 288, 30);
        newproduct.setFont(font2);
        newproduct.addActionListener(e -> {
            long uuid = 0;
            for (Product prod : productList) {
                if (prod.getUuid() > uuid) uuid = prod.getUuid();
            }

            String balance = price2.getText();
            BigDecimal bd = new BigDecimal(balance);
            long bal = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue();

            Product prod = new Product(
                    productDB,
                    uuid + 1,
                    name2.getText(),
                    bal,
                    Integer.parseInt(quantity2.getText()),
                    "");

            price2.setText("");
            quantity2.setText("");
            name2.setText("");

            productDB.add(prod);
        });
        add(newproduct);

        Button begoneproduct = new Button("Remove product");
        begoneproduct.setBounds(460, 410, 300, 30);
        begoneproduct.setFont(font2);
        begoneproduct.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            productDB.remove(productList.get(index));
            name.setText("");
            price.setText("");
            description.setText("");
            quantity.setText("");

        });

        add(begoneproduct);

        Button saveProduct = new Button("Save product");
        saveProduct.setBounds(460, 370, 300, 30);
        saveProduct.setFont(font2);
        saveProduct.addActionListener(e -> {
            String balance = price.getText();
            BigDecimal bd = new BigDecimal(balance);
            long bal = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue();

            Product tempProd = new Product(
                    productDB,
                    selectedProduct.getUuid(),
                    name.getText(),
                    bal,
                    Integer.parseInt(quantity.getText()),
                    description.getText()
            );
            productDB.modify(tempProd);
        });

        add(saveProduct);


        back = new BackgroundPanel();
        back.setBounds(0, 0, 1280, 720);
        //add(back);

        setLayout(null);
        setBounds(0, 90, 1280, 660);


    }
    void updateAdminList(){
        stock.removeAll();
        for (Product stk : productList) {
            stock.add(stk.getName());
        }
    }

    void itemClicked() {
        int index = stock.getSelectedIndex();
        if (index >= 0) {
            selectedProduct = productList.get(index);
            long bal = selectedProduct.getPrice();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            price.setText(String.valueOf(bal / 100) + '.' + dec);
            name.setText(selectedProduct.getName());
            quantity.setText(Integer.toString(selectedProduct.getQuantity()));
            description.setText(selectedProduct.getDescription());
        }
    }

    @Override
    public void dataBaseChanged(){
        makeSearch(true);
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
        updateAdminList();
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
