package com.pavogt.javaisland.screen;

import com.pavogt.javaisland.LoginManager;
import com.pavogt.javaisland.data.*;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;

public class Clients extends Panel implements DataBaseListener, KeyListener {

    private List list;
    private TextArea search;
    private TextArea name;
    private TextArea email;
    private TextArea balance;
    private TextArea email2;
    private TextArea balance2;
    private TextArea name2;
    private TextArea password2;
    private List history;
    private final ClientDataBase clientDB;
    private final LoginManager loginManager;

    private final ArrayList<Client> clientList;

    private Client selectedClient = null;

    public Clients(ClientDataBase clientDB, LoginManager lm) {
        this.clientDB = clientDB;
        this.clientDB.addListener(this);
        this.loginManager = lm;

        this.clientList = new ArrayList<>(clientDB.getData());

        makeScreen();
    }

    void makeScreen() {

        search = new TextArea("", 1, 30, TextArea.SCROLLBARS_NONE);
        search.setBounds(20, 20, 340, 30);
        search.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        search.addKeyListener(this);
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
        Label lname = new Label("Name:");
        lname.setBounds(380,20,70,30);
        lname.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lname);

        email = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        email.setBounds(460, 70, 300, 30);
        email.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(email);
        Label lemail = new Label("E-mail:");
        lemail.setBounds(380,70,70,30);
        lemail.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lemail);

        balance = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        balance.setBounds(460, 120, 300, 30);
        balance.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(balance);
        Label lbalance = new Label("Balance:");
        lbalance.setBounds(380,120,70,30);
        lbalance.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(lbalance);

        history = new List(100, false);
        history.setBounds(460, 300, 300, 300);
        add(history);

        Label namelabel = new Label("Name:");
        namelabel.setBounds(1015, 100, 120, 30);
        namelabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(namelabel);
        name2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        name2.setBounds(925, 150, 250, 30);
        name2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(name2);

        Label emaillabel = new Label("E-mail:");
        emaillabel.setBounds(1015, 200, 120, 30);
        emaillabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(emaillabel);
        email2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        email2.setBounds(925, 250, 250, 30);
        email2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(email2);

        Label passwordlabel = new Label("Password:");
        passwordlabel.setBounds(1015, 300, 120, 30);
        passwordlabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(passwordlabel);
        password2 = new TextArea("", 1, 100, TextArea.SCROLLBARS_NONE);
        password2.setBounds(925, 350, 250,30);
        password2.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(password2);

        Label historyLabel = new Label("Purchase History");
        historyLabel.setBounds(540,260,300,30);
        historyLabel.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        add(historyLabel);

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
            String balance = balance2.getText();
            BigDecimal bd = new BigDecimal(balance);
            long bal = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue();
            loginManager.register(name2.getText(), password2.getText(), email2.getText(), bal);

            name2.setText("");
            email2.setText("");
            balance2.setText("");
            password2.setText("");
        });
        add(newclient);

        Button begoneclient = new Button("Remove client");
        begoneclient.setBounds(460,220, 300,30);
        begoneclient.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        begoneclient.addActionListener(e -> {
            clientDB.remove(selectedClient);
            name.setText("");
            email.setText("");
            balance.setText("");
        });

        Button saveclient = new Button("Save client");
        saveclient.setBounds(460,170, 300,30);
        saveclient.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        saveclient.addActionListener(e -> {
            Client c = selectedClient;

            String balText = balance.getText();
            BigDecimal bd = new BigDecimal(balText);
            long bal = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue();

            Client tempClient = new Client(
                    clientDB,
                    c.getUuid(),
                    name.getText(),
                    email.getText(),
                    c.getPassword(),
                    bal,
                    false,
                    c.getHistory());
            clientDB.modify(tempClient);
        });

        add(saveclient);

        add(begoneclient);

        setLayout(null);
        setBounds(0, 90, 1280, 660);

        Button removeHistory = new Button("Remove selected transaction");
        removeHistory.setBounds(460,610, 300,30);
        removeHistory.setFont(new Font("Rockwell Nova", Font.PLAIN, 18));
        removeHistory.addActionListener(e -> {
            int index = list.getSelectedIndex();
            Client c = clientDB.getData().get(index);
            int hisindex = history.getSelectedIndex();
            ArrayList<Transaction> cHistory = c.getHistory();
            c.removeFromHistory(cHistory.get(hisindex));

        });

        add(removeHistory);

        add(saveclient);

        add(begoneclient);

        setLayout(null);
        setBounds(0, 90, 1280, 660);


    }

    void itemClicked() {
        int index = list.getSelectedIndex();
        if (index >= 0) {
            Client c = clientDB.getData().get(index);
            selectedClient = c;
            name.setText(c.getName());
            email.setText(c.getEmail());
            long bal = c.getBalance();
            String dec = String.valueOf(bal % 100);
            if (bal % 100 < 10)
                dec = '0' + dec;
            balance.setText(String.valueOf(bal / 100) + '.' + dec);

            history.removeAll();
            for (Transaction t : c.getHistory()) {
                long hisPrice = t.getPrice();
                String decs = String.valueOf(hisPrice % 100);
                if (hisPrice % 100 < 10)
                    decs = '0' + decs;

                history.add(String.valueOf(hisPrice / 100) + '.' + decs + " - " + t.getProducts().size() + " Products");
            }
        }
    }

    private void updateClientList() {
        list.removeAll();
        for (Client cli : clientList) {
            list.add(cli.getName());
        }
    }

    private String removeAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    String previousSearch = null;

    private void makeSearch(boolean update) {
        String text = removeAccents(search.getText().toLowerCase());
        if (text.equals(previousSearch) && !update) {
            return;
        }
        previousSearch = text;
        String[] parts = text.split(" ");
        ArrayList<Client> clients = clientDB.getData();
        clientList.clear();
        if (text.trim().length() == 0) {
            clientList.addAll(clients);

        } else {
            for (Client client : clients) {
                String name = removeAccents(client.getName().toLowerCase());
                boolean show = true;
                for (String part : parts) {
                    if (!name.contains(part)) {
                        show = false;
                        break;
                    }
                }
                if (show) {
                    clientList.add(client);
                }
            }
        }
        updateClientList();
    }

    @Override
    public void dataBaseChanged() {
        makeSearch(true);

        history.removeAll();
        if (selectedClient != null) {
            itemClicked();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        makeSearch(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        makeSearch(false);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        makeSearch(false);
    }
}
