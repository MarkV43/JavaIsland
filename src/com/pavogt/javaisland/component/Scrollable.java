package com.pavogt.javaisland.component;

import java.awt.*;

public class Scrollable extends Frame {
    public static void main(String[] args) {

        Frame frame = new Frame("Scrollbar");

        frame.setLayout(new FlowLayout());

        ScrollPane sp = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

        Panel p = new Panel(new FlowLayout());
        p.setPreferredSize(new Dimension(400, 300));

        for (int i = 0; i < 2; i++) {
            Button b = new Button(Integer.toString(i));
            p.add(b);
        }

        sp.add(p);

        frame.add(sp);

        frame.setVisible(true);

        frame.pack();

    }
}
