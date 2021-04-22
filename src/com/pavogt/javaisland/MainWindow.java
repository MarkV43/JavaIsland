package com.pavogt.javaisland;

import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.ProductDataBase;
import com.pavogt.javaisland.screen.Admin;
import com.pavogt.javaisland.screen.Clients;
import com.pavogt.javaisland.screen.Initial;
import com.pavogt.javaisland.screen.Store;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends Frame {

    private Panel clientsPanel;
    private Panel adminPanel;
    private Store storePanel;

    private ClientDataBase clientDB;
    private ProductDataBase productDB;

    public MainWindow() {
        super();

        clientDB = new ClientDataBase("client.dat");
        productDB = new ProductDataBase("product.dat");

        /*try {
            clientDB.setData(new ArrayList<>());
            clientDB.store();
            productDB.setData(new ArrayList<>());
            productDB.store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;*/

        try {
            clientDB.read();
            productDB.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        clients.setBounds(0, 30, 430, 60);
        clients.addActionListener(e -> {
            clientsPanel.setVisible(true);
            adminPanel.setVisible(false);
            storePanel.setVisible(false);
        });
        add(clients);

        Button admin = new Button("Admin");
        admin.setBounds(430, 30, 420, 60);
        admin.addActionListener(e -> {
            clientsPanel.setVisible(false);
            adminPanel.setVisible(true);
            storePanel.setVisible(false);
        });
        add(admin);

        Button store = new Button("Loja");
        store.setBounds(850, 30, 430, 60);
        store.addActionListener(e -> {
            clientsPanel.setVisible(false);
            adminPanel.setVisible(false);
            storePanel.setVisible(true);
        });
        add(store);

        Panel initialPanel = new Initial();

        clientsPanel = new Clients(clientDB);
        adminPanel = new Admin(productDB);
        storePanel = new Store(clientDB, productDB);

        clientsPanel.setVisible(true);
        adminPanel.setVisible(false);
        storePanel.setVisible(false);
        add(clientsPanel);
        add(adminPanel);
        add(storePanel);

        add(initialPanel);

        setTitle("Java Island");
        setLayout(null);
        setVisible(true);
        setSize(1280, 750);
    }
}
