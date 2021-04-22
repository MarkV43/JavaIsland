package com.pavogt.javaisland.data;

public class ClientDataBase extends DataBase<Client> {

    public ClientDataBase(String filename) {
        super(filename);
    }

    @Override
    Client cast(Object obj) {
        return (Client) obj;
    }
}
