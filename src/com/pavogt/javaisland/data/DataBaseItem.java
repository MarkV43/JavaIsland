package com.pavogt.javaisland.data;

import java.io.Serializable;

public abstract interface DataBaseItem extends Serializable {
    long getUuid();
}
