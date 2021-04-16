package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin extends Panel {

    private List stock;
    private Label name;
    private Label price;
    private Label quantity;
    private Product[] products;

    public Admin(){

        products = new Product[100];
        products[0] = new Product(0, "RTX 3080Ti", 12034.58f, 3, "");
        products[1] = new Product(1, "RTX 3070Ti", 10422.66f, 12, "");
        products[2] = new Product(2, "RTX 3060Ti", 8050.36f, 5, "");
        products[3] = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[4] = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[5] = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[6] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");

        Button bAdd = new Button("+");
        bAdd.setBounds(50, 50, 30, 30);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 25);
        bAdd.setFont(font);
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int index = stock.getSelectedIndex();
                Product h = products[index];
                h.setQuantity(h.getQuantity()+1);
                quantity.setText("Quantidade: " + h.getQuantity());
            }
        });
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(400,50,30,30);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        bRem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int index = stock.getSelectedIndex();
                Product h = products[index];
                h.setQuantity(h.getQuantity()-1);
                quantity.setText("Quantidade: " + h.getQuantity());
            }
        });
        add(bRem);

        TextArea search = new TextArea("",1,100, TextArea.SCROLLBARS_NONE);
        search.setBounds(100,50, 288,30);
        search.setFont(font2);
        add(search);

        stock = new List(100, false);

        for (int i = 0; i < 100; i++) {
            if (products[i] == null) break;
            stock.add(products[i].getName());
        }

        stock.setBounds(100,100, 288,300);
        add(stock);
        
        
        
        setBackground(Color.cyan);

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
        
        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }
    void itemClicked() {
        int index = stock.getSelectedIndex();
        Product c = products[index];
        name.setText(c.getName());
        price.setText("R$ " + Float.toString(c.getPrice()));
        quantity.setText("Quantidade: " + c.getQuantity());
    }
}
