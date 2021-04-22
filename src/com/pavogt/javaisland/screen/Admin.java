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
    private Label name;
    private Label price;
    private Label quantity;
    private Label add;
    private ArrayList<Product> products;
    BackgroundPanel back;
    private ProductDataBase productDB;

    public Admin(ProductDataBase productDB) {

        this.productDB = productDB;
        this.productDB.addListener(this);

        /*var _products = new Product[100];
        _products[0] = new Product(0, "RTX 3080Ti", 12034.58f, 3, "");
        _products[1] = new Product(1, "RTX 3070Ti", 10422.66f, 12, "");
        _products[2] = new Product(2, "RTX 3060Ti", 8050.36f, 5, "");
        _products[3] = new Product(3, "RTX 3070", 9003.25f, 1, "");
        _products[4] = new Product(4, "RTX 3060", 5036.78f, 7, "");
        _products[5] = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        _products[6] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");*/

        products = this.productDB.getData();

        System.out.println("products = " + products);

        Button bAdd = new Button("+");
        bAdd.setBounds(50, 50, 30, 30);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 25);
        bAdd.setFont(font);
        bAdd.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            Product h = products.get(index);
            h.setQuantity(h.getQuantity() + 1);
            quantity.setText("Quantidade: " + h.getQuantity());
        });
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(400, 50, 30, 30);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        bRem.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            Product h = products.get(index);
            h.setQuantity(h.getQuantity() - 1);
            quantity.setText("Quantidade: " + h.getQuantity());
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

        name = new Label("");
        name.setBounds(480, 100, 300, 30);
        name.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name);

        price = new Label("");
        price.setBounds(480, 150, 300, 30);
        price.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(price);

        quantity = new Label("");
        quantity.setBounds(480, 200, 300, 30);
        quantity.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(quantity);

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

            productDB.add(prod);
        });
        add(newproduct);

        Button begoneproduct = new Button("Remover produto");
        begoneproduct.setBounds(100,450, 288,30);
        begoneproduct.setFont(font2);
        begoneproduct.addActionListener(e -> {
            int index = stock.getSelectedIndex();
            stock.remove(index);
                });

        add(begoneproduct);



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
        price.setText("R$ " + Float.toString(c.getPrice()));
        quantity.setText("Quantidade: " + c.getQuantity());
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
