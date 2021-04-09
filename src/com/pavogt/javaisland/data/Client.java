package com.pavogt.javaisland.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    public static final long serialVersionUID = 3L;

    private final long uuid;
    private String name;
    private String email;
    private long password;
    private float balance;
    private boolean admin;
    final private ArrayList<Transaction> history;

    public Client(long uuid, String name, String email, long password, float balance, boolean admin, ArrayList<Transaction> history) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.history = history;
        this.admin = admin;
    }

    public Client(long uuid, String name, String email, long password, float balance, boolean admin) {
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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
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
                '}';
    }
}
