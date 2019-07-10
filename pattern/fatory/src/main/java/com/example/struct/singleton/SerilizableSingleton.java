package com.example.struct.singleton;

import java.io.Serializable;

/**
 * lingfan
 * 2019-05-31 16:30
 */
public class SerilizableSingleton implements Serializable {

    private static SerilizableSingleton instance = new SerilizableSingleton();

    private SerilizableSingleton() {

    }


    public static SerilizableSingleton getInstance() {
        return instance;
    }


    public Object readResolve() {
        return instance;
    }

}
