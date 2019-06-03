package com.example.struct.singleton;

/**
 * lingfan
 * 2019-05-29 17:56
 */
public class HungrySingleton {

    private volatile  static  HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton(){ }

    public static HungrySingleton getInstance(){
        return singleton;
    }

}
