package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.data.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Float.parseFloat;

public class Clients extends Panel implements DataBaseListener {

    private List list;
    private TextArea name;
    private TextArea email;
    private TextArea balance;
    private TextArea email2;
    private TextArea balance2;
    private TextArea name2;
    private TextArea password2;
    private Label lname;
    private Label lbalance;
    private Label lemail;
    private List history;
    private final ClientDataBase clientDB;
    private ArrayList<Client> clients;
    private ArrayList<Transaction> history2;

    public Clients(ClientDataBase clientDB) {
        this.clientDB = clientDB;
        this.clientDB.addListener(this);

        makeScreen();
    }

    void makeScreen() {
        Button bAdd = new Button("+");
        bAdd.setBounds(20, 20, 30, 30);
        Font font = new Font("Rockwell Nova", Font.PLAIN, 28);
        bAdd.setFont(font);
        add(bAdd);

        TextArea search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(60, 20, 300, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(search);

        list = new List(100, false);
        for (Client client: clientDB.getData()) {
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

        name = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        name.setBounds(460, 20, 300, 30);
        name.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name);
        lname = new Label("Name:");
        lname.setBounds(380,20,70,30);
        lname.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lname);

        email = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        email.setBounds(460, 70, 300, 30);
        email.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(email);
        lemail = new Label("E-mail:");
        lemail.setBounds(380,70,70,30);
        lemail.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lemail);

        balance = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        balance.setBounds(460, 120, 300, 30);
        balance.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(balance);
        lbalance = new Label("Balance:");
        lbalance.setBounds(380,120,70,30);
        lbalance.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lbalance);

        Label namelabel = new Label("Name:");
        namelabel.setBounds(1015, 100, 120, 30);
        namelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel);

        Label emaillabel = new Label("E-mail:");
        emaillabel.setBounds(1015, 200, 120, 30);
        emaillabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(emaillabel);

        Label passwordlabel = new Label("Password:");
        passwordlabel.setBounds(1015, 300, 120, 30);
        passwordlabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(passwordlabel);


        Label balancelabel = new Label("Balance:");
        balancelabel.setBounds(1015, 400, 140, 30);
        balancelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(balancelabel);
        balance2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        balance2.setBounds(925, 450, 250, 30);
        balance2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(balance2);

        Button newclient = new Button("Add client");
        newclient.setBounds(905, 500, 288, 30);
        newclient.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        newclient.addActionListener(e -> {
            long uuid = 0;
            for (Client clien : clientDB.getData()) {
                if (clien.getUuid() > uuid) uuid = clien.getUuid();
            }

            Client clien = new Client(
                    uuid + 1,
                    name2.getText(),
                    email2.getText(),
                    Long.parseLong(password2.getText()),
                    parseFloat(balance2.getText()),
                    false, history2);

            name2.setText("");
            email2.setText("");
            balance2.setText("");
            password2.setText("");

            clientDB.add(clien);
        });
        add(newclient);

        Button begoneclient = new Button("Remove client");
        begoneclient.setBounds(460,220, 300,30);
        begoneclient.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        begoneclient.addActionListener(e -> {
            int index = list.getSelectedIndex();
            clientDB.remove(index);
            name.setText("");
            email.setText("");
            balance.setText("");

        });

        Button saveclient = new Button("Save client");
        saveclient.setBounds(460,170, 300,30);
        saveclient.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        saveclient.addActionListener(e -> {
            Client tempprod;
            int index = list.getSelectedIndex();
            Client c = clientDB.getData().get(index);
            tempprod = new Client( c.getUuid(),
                    name.getText(),
                    email.getText(),
                    c.getPassword(),
                    Float.parseFloat(balance.getText()),
                    false,
                    history2);
            clientDB.mod(index, tempprod);
        });

        add(saveclient);

        add(begoneclient);

        setLayout(null);
        setBounds(0, 90, 1280, 660);
        
        
    }

    void itemClicked() {
        int index = list.getSelectedIndex();
        Client c = clientDB.getData().get(index);
        name.setText(c.getName());
        email.setText(c.getEmail());
        balance.setText(Float.toString(c.getBalance()));
    }

    @Override
    public void dataBaseChanged() {
        list.removeAll();
        for (Client cli : clientDB.getData()) {
            list.add(cli.getName());
        }
    }
}
