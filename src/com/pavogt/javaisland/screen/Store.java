package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.component.Scrollable;
import com.pavogt.javaisland.data.Product;

import javax.swing.*;
import java.awt.*;

public class Store extends Panel {

    Scrollable cart;
    Product[] products;

    public Store() {
        super();

        products = new Product[100];
        products[0] = new Product(0, "RTX 3080Ti", 12034.58f, 3, "");
        products[1] = new Product(1, "RTX 3070Ti", 10422.66f, 12, "");
        products[2] = new Product(2, "RTX 3060Ti", 8050.36f, 5, "");
        products[3] = new Product(3, "RTX 3070", 9003.25f, 1, "");
        products[4] = new Product(4, "RTX 3060", 5036.78f, 7, "");
        products[5] = new Product(5, "Monitor Curvo 1444p", 2230.47f, 6, "");
        products[6] = new Product(6, "Mouse Gamer 7200dpi", 320.54f, 18, "");

//        cart = new Scrollable(new GridLayout(100, 1));

        for (Product product : products) {
            if (product == null) break;
            Panel p = new Panel();
            p.add(new Label(product.getName()));
            p.add(new Label("R$ " + product.getPrice()));
            p.setBackground(Color.RED);
            add(p);
        }

//        setLayout();
        setBounds(0, 90, 1280, 660);
    }
}
