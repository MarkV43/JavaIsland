package com.pavogt.javaisland.data;

public class ProductDataBase extends DataBase<Product> {

    public ProductDataBase(String filename) {
        super(filename);
    }

    @Override
    Product cast(Object obj) {
        return (Product) obj;
    }
}
