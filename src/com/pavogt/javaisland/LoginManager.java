package com.pavogt.javaisland;

import com.pavogt.javaisland.data.Client;
import com.pavogt.javaisland.data.ClientDataBase;
import com.pavogt.javaisland.data.DataBaseListener;
import com.pavogt.javaisland.data.LoginListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;

public class LoginManager implements DataBaseListener {
    private final ClientDataBase db;
    private Client loggedUser = null;
    private MessageDigest digest;
    private final ArrayList<LoginListener> listeners;

    public LoginManager(ClientDataBase db) {
        this.db = db;
        listeners = new ArrayList<>(10);
        this.db.addListener(this);
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListener(LoginListener l) {
        listeners.add(l);
    }

    private byte[] hash(String str) {
        return digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }

    private long stringHash(String str) {
        byte[] hash = hash(str);
        long val = 0;
        for (int i = 0; i < hash.length; i++) {
            val += ((long) hash[i] & 0xffL) << (8 * i);
            val += ((long) hash[i] & 0xffL) >> (64 - 8 * i);
        }
        return val;
    }

    private String generateSalt(String a) {
        return Base64.getEncoder().encodeToString(hash(a));
    }

    public long hashPassword(String username, String password) {
        String salt0 = generateSalt(username + password);
        String salt1 = generateSalt(username + salt0);
        String salt2 = generateSalt(password + salt1);
        String salt3 = generateSalt(salt0 + salt1 + salt2);

        return stringHash(username + password + salt0 + salt1 + salt2 + salt3);
    }

    public Client getUser(String username) {
        Client user = null;
        for (int i = 0; i < db.getData().size(); i++) {
            user = db.getData().get(i);
            if (user.getEmail().equals(username)) break;
        }
        return user;
    }

    public void login(String username, String password) {
        long hash = hashPassword(username, password);
        Client user = getUser(username);
        if (user.getPassword() == hash) {
            loggedUser = user;
            for (LoginListener l : listeners) {
                l.loginChanged();
            }
        }
    }

    public void register(String username, String password, String email, long balance) {
        long hash = hashPassword(username, password);
        long uuid = db.getNextUuid();
        Client user = new Client(db, uuid, username, email, hash, balance, false);
        db.add(user);
//        loggedUser = user;
        for (LoginListener l : listeners) {
            l.loginChanged();
        }
    }

    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    public Client getLoggedUser() {
        return loggedUser;
    }

    @Override
    public void dataBaseChanged() {
        for (LoginListener l : listeners) {
            l.loginChanged();
        }
    }
}
