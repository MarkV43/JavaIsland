package com.pavogt.javaisland.data;

import java.io.*;
import java.util.ArrayList;

public abstract class DataBase<T extends Serializable> {
    private ArrayList<T> data;
    private final String filename;

    public DataBase(String filename) {
        this.filename = filename;
    }

    public void store() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void read() throws IOException, ClassNotFoundException {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object[] objs = (Object[]) objectInputStream.readObject();
            data = cast(objs);
            objectInputStream.close();
        } catch (Exception e) {
            data = new ArrayList<>();
        }
    }

    abstract ArrayList<T> cast(Object[] array);

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
