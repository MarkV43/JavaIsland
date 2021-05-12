package com.pavogt.javaisland.data;

import com.pavogt.javaisland.screen.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class DataBase<T extends DataBaseItem> {
    private ArrayList<T> data;
    private final String filename;
    private final ArrayList<DataBaseListener> listeners;

    public DataBase(String filename) {
        this.filename = filename;
        listeners = new ArrayList<>(10);
    }

    public void store() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void read() throws ClassNotFoundException {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            ArrayList<Object> objs = new ArrayList<>((Collection<?>) objectInputStream.readObject());
            data = objs.stream().map(this::cast).peek(t -> {
                t.setDatabase(this);
            }).collect(Collectors.toCollection(ArrayList::new));;
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            data = new ArrayList<>();
        }
    }

    public void add(T obj) {
        data.add(obj);
        notifyChanges();
    }

    public void mod(int index, T obj) {
        data.set(index, obj);
        notifyChanges();
    }

    public void remove(int index){
        data.remove(index);
        notifyChanges();
    }

    public void remove(T obj) {
        data.remove(obj);
        notifyChanges();
    }

    abstract T cast(Object obj);

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public void addListener(DataBaseListener listener) {
        listeners.add(listener);
    }

    public long getNextUuid() {
        long uuid = -1;
        for (T t: data) {
            if (t.getUuid() > uuid)
                uuid = t.getUuid();
        }
        return uuid + 1;
    }

    public T getFromUuid(long uuid) {
        for (T t: data) {
            if (t.getUuid() == uuid)
                return t;
        }
        return null;
    }

    public void notifyChanges() {
        for (DataBaseListener i : listeners) {
            i.dataBaseChanged();
        }
    }
}
