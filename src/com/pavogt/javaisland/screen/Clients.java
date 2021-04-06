package com.pavogt.javaisland.screen;

import java.awt.*;

public class Clients extends Panel {
    public Clients() {
        Button bAdd = new Button("+");
        bAdd.setBounds(50, 50, 100, 100);
        bAdd.setBackground(Color.BLACK);
        bAdd.setForeground(Color.WHITE);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        bAdd.setFont(font);
        add(bAdd);

        setBackground(Color.BLUE);
        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }
}
