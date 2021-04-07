package com.pavogt.javaisland.data;

import java.io.Serializable;

public class Client implements Serializable {

    public static final long serialVersionUID = 2L;

    private final long uuid;
    private String name;
    private String email;
    private long password;
    private float balance;

    public Client(long uuid, String name, String email, long password, float balance) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public Client(long uuid, String name, String email, long password) {
        this(uuid, name, email, password, 0);
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
