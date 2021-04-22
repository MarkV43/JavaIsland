package com.pavogt.javaisland.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

    public void read() throws ClassNotFoundException {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            ArrayList<Object> objs = new ArrayList<>((Collection<?>) objectInputStream.readObject());
            data = objs.stream().map(this::cast).collect(Collectors.toCollection(ArrayList::new));;
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            data = new ArrayList<>();
        }
    }

    abstract T cast(Object obj);

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
