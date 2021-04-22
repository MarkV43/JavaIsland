package com.pavogt.javaisland.data;

import java.io.Serializable;

public class Transaction implements Serializable {

    public static final long serialVersionUID = 1L;

    private long uuid;
    private float price;

    private long[] products;
    private int[] amounts;

}
