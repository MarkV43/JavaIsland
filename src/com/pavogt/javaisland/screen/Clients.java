package com.pavogt.javaisland.screen;

import java.awt.*;

public class Clients extends Panel {
    public Clients() {
        Button bAdd = new Button("+");
        bAdd.setBounds(20, 20, 30, 30);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 28);
        bAdd.setFont(font);
        add(bAdd);

        TextArea search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(60, 20, 300, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(search);

        List list = new List(3, false);
        list.add("Marcelo");
        list.add("Gaby");
        list.add("Luís Eduardo");
        list.add("Eduardo");
        list.add("Gabriel");
        list.add("Marechal Luciolo");
        list.setBounds(20, 60, 340, 580);
        add(list);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }
}
