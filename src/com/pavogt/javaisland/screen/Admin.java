package com.pavogt.javaisland.screen;

import java.awt.*;

public class Admin extends Panel {
    public Admin(){
        Button bAdd = new Button("+");
        bAdd.setBounds(50, 50, 100, 100);
        bAdd.setBackground(Color.GREEN);
        bAdd.setForeground(Color.BLACK);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        Font font2 = new Font("Rockwell Nova", Font.PLAIN, 25);
        bAdd.setFont(font);
        add(bAdd);

        Button bRem = new Button("-");
        bRem.setBounds(400,50,100,100);
        bRem.setBackground(Color.RED);
        bRem.setForeground(Color.BLACK);
        bRem.setFont(font);
        add(bRem);

        TextArea search = new TextArea("",1,100, TextArea.SCROLLBARS_NONE);
        search.setBounds(170,50, 218,100);
        search.setFont(font2);
        add(search);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

}
