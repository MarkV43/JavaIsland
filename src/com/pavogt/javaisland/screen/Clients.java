package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.ClientDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Clients extends Panel {

    private ArrayList<Client> clients;
    private List list;
    private Label name;
    private Label email;
    private Label balance;
    private List history;
    private ClientDataBase clientDB;

    public Clients(ClientDataBase clientDB) {
        this.clientDB = clientDB;

        Button bAdd = new Button("+");
        bAdd.setBounds(20, 20, 30, 30);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 28);
        bAdd.setFont(font);
        add(bAdd);

        TextArea search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(60, 20, 300, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(search);

        clients = this.clientDB.getData();

        System.out.println("clients = " + clients);

        list = new List(100, false);
        for (Client client: clients) {
            list.add(client.getName());
        }
        list.setBounds(20, 60, 340, 580);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemClicked();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                itemClicked();
            }
        });
        list.addActionListener(e -> itemClicked());
        add(list);

        name = new Label("");
        name.setBounds(380, 20, 300, 30);
        name.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name);

        email = new Label("");
        email.setBounds(380, 70, 300, 30);
        email.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(email);

        balance = new Label("");
        balance.setBounds(380, 120, 300, 30);
        balance.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(balance);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
    }

    void itemClicked() {
        int index = list.getSelectedIndex();
        Client c = clients.get(index);
        name.setText(c.getName());
        email.setText(c.getEmail());
        balance.setText("R$ " + c.getBalance());
    }
}
