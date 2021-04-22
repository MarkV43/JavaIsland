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

        System.out.println("products = " + products);

        Button bAdd = new Button("+");
        bAdd.setBounds(440, 200, 30, 30);
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
        bRem.setBounds(790, 200, 30, 30);
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
        search.setBounds(100, 50, 288, 30);
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

        stock.setBounds(100, 100, 288, 300);
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
        name.setBounds(620, 100, 160, 30);
        name.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name);

        price = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        price.setBounds(620, 150, 160, 30);
        price.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(price);

        quantity = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        quantity.setBounds(620, 200, 160, 30);
        quantity.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantity);

        description = new TextArea("",6,100, TextArea.SCROLLBARS_NONE);
        description.setBounds(620, 250, 160,100);
        description.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(description);

        add = new Label("Adicionar produto:");
        add.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add.setBounds(1000, 50, 150, 30);
        add(add);

        Label namelabel = new Label("Product name:");
        namelabel.setBounds(1015, 100, 120, 30);
        namelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel);

        Label pricelabel = new Label("Product price:");
        pricelabel.setBounds(1015, 300, 120, 30);
        pricelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(pricelabel);

        Label quantitylabel = new Label("Product quantity:");
        quantitylabel.setBounds(1015, 200, 140, 30);
        quantitylabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantitylabel);

        Label namelabel2 = new Label("Product name:");
        namelabel2.setBounds(480, 100, 140, 30);
        namelabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel2);

        Label pricelabel2 = new Label("Product price: R$");
        pricelabel2.setBounds(480, 150, 140, 30);
        pricelabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(pricelabel2);

        Label quantitylabel2 = new Label("Product quantity:");
        quantitylabel2.setBounds(480, 200, 140, 30);
        quantitylabel2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantitylabel2);

        Label description2 = new Label("Description:");
        description2.setBounds(480, 250, 140, 30);
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

        Button begoneproduct = new Button("Remover produto");
        begoneproduct.setBounds(486,410, 288,30);
        begoneproduct.setFont(font2);
        begoneproduct.addActionListener(e -> {
                    int index = stock.getSelectedIndex();
                    productDB.remove(index);

                });

        add(begoneproduct);

        Button saveproduct = new Button("Salvar produto");
        saveproduct.setBounds(486,370, 288,30);
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
        add(back);

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
        System.out.println("Estive aqui!");
        stock.removeAll();
        for (Product stk : productDB.getData()) {
            stock.add(stk.getName());

        }
    }
}
