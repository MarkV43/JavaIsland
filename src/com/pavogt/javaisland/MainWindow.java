package com.pavogt.javaisland;

import com.pavogt.javaisland.data.CartListener;
import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.ProductDataBase;
import com.pavogt.javaisland.screen.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainWindow extends Frame implements CartListener {

    private Panel clientsPanel;
    private Panel adminPanel;
    private Panel storePanel;
    private Panel cartPanel;

    private final ClientDataBase clientDB;
    private final ProductDataBase productDB;
    private final LoginManager loginManager;
    private final CartManager cartManager;
    private Button cart;

    public MainWindow() {
        super();

        clientDB = new ClientDataBase("client.dat");
        productDB = new ProductDataBase("product.dat");
        loginManager = new LoginManager(clientDB);
        cartManager = new CartManager();

        cartManager.addListener(this);

        try {
            clientDB.read();
            productDB.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Client c = clientDB.getFromUuid(0);
        c.setPassword(loginManager.hashPassword(c.getEmail(), "abc123"));

        Client c2 = clientDB.getFromUuid(2);
        c2.setPassword(loginManager.hashPassword(c2.getEmail(), "123456"));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    clientDB.store();
                    productDB.store();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                dispose();
            }
        });

        Button clients = new Button("Clientes");
        clients.setBounds(0, 30, 320, 60);
        clients.addActionListener(e -> {
            clientsPanel.setVisible(true);
            adminPanel.setVisible(false);
            storePanel.setVisible(false);
            cartPanel.setVisible(false);
        });
        add(clients);

        Button admin = new Button("Admin");
        admin.setBounds(320, 30, 320, 60);
        admin.addActionListener(e -> {
            clientsPanel.setVisible(false);
            adminPanel.setVisible(true);
            storePanel.setVisible(false);
            cartPanel.setVisible(false);
        });
        add(admin);

        Button store = new Button("Loja");
        store.setBounds(640, 30, 320, 60);
        store.addActionListener(e -> {
            clientsPanel.setVisible(false);
            adminPanel.setVisible(false);
            storePanel.setVisible(true);
            cartPanel.setVisible(false);
        });
        add(store);

        cart = new Button("Carrinho (" + cartManager.getSize() + ")");
        cart.setBounds(960, 30, 320, 60);
        cart.addActionListener(e -> {
            clientsPanel.setVisible(false);
            adminPanel.setVisible(false);
            storePanel.setVisible(false);
            cartPanel.setVisible(true);
        });
        add(cart);

        Panel initialPanel = new Initial();

        clientsPanel = new Clients(clientDB);
        adminPanel = new Admin(productDB);
        storePanel = new Store(clientDB, productDB, cartManager);
        cartPanel = new Cart(clientDB, productDB, loginManager, cartManager);

        clientsPanel.setVisible(true);
        adminPanel.setVisible(false);
        storePanel.setVisible(false);
        add(clientsPanel);
        add(adminPanel);
        add(storePanel);
        add(cartPanel);

        add(initialPanel);

        setTitle("Java Island");
        setLayout(null);
        setVisible(true);
        setSize(1280, 750);
    }

    @Override
    public void cartChanged() {
        cart.setLabel("Carrinho (" + cartManager.getSize() + ")");
    }
}
