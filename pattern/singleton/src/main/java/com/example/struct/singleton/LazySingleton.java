package com.example.struct.singleton;

/**
 * lingfan
 * 2019-05-29 17:59
 */
public class LazySingleton {

    private static LazySingleton singleton = new LazySingleton();

    private LazySingleton(){ }

    public LazySingleton getInstance(){
        if(singleton == null){
            synchronized (LazySingleton.class){
                if(singleton == null ){
                    singleton = new LazySingleton();
                }
            }
        }
        return singleton;
    }
}
