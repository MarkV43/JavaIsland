package com.pavogt.javaisland.data;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductDataBase extends DataBase<Product> {

    public ProductDataBase(String filename) {
        super(filename);
    }

    @Override
    ArrayList<Product> cast(Object[] array) {
        return new ArrayList<>(Arrays.asList((Product[]) array));
    }
}
