package com.pavogt.javaisland.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements DataBaseItem {

    public static final long serialVersionUID = 4L;

    private final long uuid;
    private String name;
    private String email;
    private long password;
    private int balance;
    private boolean admin;
    private ArrayList<Transaction> history;

    public Client(long uuid, String name, String email, long password, int balance, boolean admin, ArrayList<Transaction> history) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.history = history;
        this.admin = admin;
    }

    public Client(long uuid, String name, String email, long password, int balance, boolean admin) {
        this(uuid, name, email, password, balance, admin, new ArrayList<>());
    }

    public long getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Transaction> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Transaction> history) {
        this.history = history;
    }

    public void addToHistory(Transaction t) {
        history.add(t);
    }

    public boolean removeFromHistory(Transaction t) {
        return history.remove(t);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", balance=" + balance +
                ", history=" + listToString(history) +
                '}';
    }

    private static String listToString(ArrayList<Transaction> list) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Transaction t : list) {
            if (!first) sb.append(", ");
            else first = false;
            sb.append(t.toString());
        }
        sb.append(']');
        return sb.toString();
    }
}
