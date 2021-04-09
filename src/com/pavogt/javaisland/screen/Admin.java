package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.Product;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin extends Panel {

    private List stock;
    private Label name;
    private Label price;
    private Label quantity;
    private Product[] products;

    public Admin(){
        Button bAdd = new Button("+");
        bAdd.setBounds(50, 50, 30, 30);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 25);
        bAdd.setFont(font);
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(400,50,30,30);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        add(bRem);

        TextArea search = new TextArea("",1,100, TextArea.SCROLLBARS_NONE);
        search.setBounds(100,50, 288,30);
        search.setFont(font2);
        add(search);

        List results = new List(100,false);
        results.setBounds(100,100, 288,300);
        add(results);

        List stock = new List(100, false);
        stock.add("RTX 3080Ti");
        stock.add("RTX 3070Ti");
        stock.add("RTX 3060Ti");
        stock.add("RTX 3060");
        stock.add("Mouse Redragon");
        stock.add("Cyanide");
        stock.add("RGB Strip Corsair");
        stock.add("Revólver");
        stock.add("Cute Dog");

        stock.setBounds(600,50,500,400);
        add(stock);


        setBackground(Color.cyan);
        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

}
