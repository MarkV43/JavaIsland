package com.pavogt.javaisland.data;

import java.io.*;

public abstract class DataBase<T extends Serializable> {
    private T[] data;
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
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object[] objs = (Object[]) objectInputStream.readObject();
        data = cast(objs);
        objectInputStream.close();
    }

    abstract T[] cast(Object[] array);
}
