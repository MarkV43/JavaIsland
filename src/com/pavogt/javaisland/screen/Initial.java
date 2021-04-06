package com.pavogt.javaisland.screen;

import java.awt.*;

public class Initial extends Panel {
    public Initial() {
        super();

        Label title = new Label("Java Island", Label.CENTER);
        title.setBounds(500, 250, 280, 120);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 50);
        title.setFont(font);
        add(title);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }
}
