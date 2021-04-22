package com.pavogt.javaisland.data;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientDataBase extends DataBase<Client> {

    public ClientDataBase(String filename) {
        super(filename);
    }

    @Override
    ArrayList<Client> cast(Object[] array) {
        return new ArrayList<>(Arrays.asList((Client[]) array));
    }
}
