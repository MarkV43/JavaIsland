package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.component.BackgroundPanel;
import com.pavogt.javaisland.data.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Admin extends Panel implements DataBaseListener {

    private List stock;
    private TextArea name;
    private TextArea price;
    private TextArea quantity;
    private TextArea description;
    private Label add;
    private ArrayList<Product> products;
    BackgroundPanel back;
    private ProductDataBase productDB;

    public Admin(ProductDataBase productDB) {

        this.productDB = productDB;
        this.productDB.addListener(this);

        products = this.productDB.getData();

        Button bAdd = new Button("+");
        bAdd.setBounds(770, 120, 30, 30);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 25);
        bAdd.setFont(font);
        bAdd.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            Product h = products.get(index);
            h.setQuantity(h.getQuantity() + 1);
            quantity.setText(Integer.toString(h.getQuantity()));
        });
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(810, 120, 30, 30);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        bRem.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            Product h = products.get(index);
            h.setQuantity(h.getQuantity() - 1);
            quantity.setText(Integer.toString(h.getQuantity()));
        });
        add(bRem);


        TextArea search = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        search.setBounds(20, 20, 340, 30);
        search.setFont(font2);
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

        for (Product p : products) {
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

        description = new TextArea("",6,100, TextArea.SCROLLBARS_NONE);
        description.setBounds(460, 170, 300,150);
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
            for (Product prod : products) {
                if (prod.getUuid() > uuid) uuid = prod.getUuid();
            }

            Product prod = new Product(
                    uuid + 1,
                    name2.getText(),
                    Float.parseFloat(price2.getText()),
                    Integer.parseInt(quantity2.getText()),
                    "");

            price2.setText("");
            quantity2.setText("");
            name2.setText("");

            productDB.add(prod);
        });
        add(newproduct);

        Button begoneproduct = new Button("Remove product");
        begoneproduct.setBounds(460,410, 300,30);
        begoneproduct.setFont(font2);
        begoneproduct.addActionListener(e -> {
                    int index = stock.getSelectedIndex();
                    productDB.remove(index);
                    name.setText("");
                    price.setText("");
                    description.setText("");
                    quantity.setText("");

                });

        add(begoneproduct);

        Button saveproduct = new Button("Save product");
        saveproduct.setBounds(460,370, 300,30);
        saveproduct.setFont(font2);
        saveproduct.addActionListener(e -> {
            Product tempprod;
            int index = stock.getSelectedIndex();
            tempprod = new Product(index,name.getText(), Float.parseFloat(price.getText()), Integer.parseInt(quantity.getText()), description.getText());
            productDB.mod(index, tempprod);
        });

        add(saveproduct);



        back = new BackgroundPanel();
        back.setBounds(0, 0, 1280, 720);
        //add(back);

        setLayout(null);
        setBounds(0, 90, 1280, 660);


    }

    void itemClicked() {
        int index = stock.getSelectedIndex();
        Product c = products.get(index);
        name.setText(c.getName());
        price.setText(Float.toString(c.getPrice()));
        quantity.setText(Integer.toString(c.getQuantity()));
        description.setText(c.getDescription());
    }

    @Override
    public void dataBaseChanged() {
        stock.removeAll();
        for (Product stk : productDB.getData()) {
            stock.add(stk.getName());

        }
    }
}
