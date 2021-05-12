package com.pavogt.javaisland.data;

import java.io.Serializable;

public abstract class DataBaseItem<T extends DataBaseItem> implements Serializable {
    private transient DataBase<T> database;

    DataBaseItem(DataBase<T> db) {
        database = db;
    }

    abstract long getUuid();

    void notifyDataBase() {
        database.notifyChanges();
    }

    void setDatabase(DataBase<T> db) {
        database = db;
    }
}
