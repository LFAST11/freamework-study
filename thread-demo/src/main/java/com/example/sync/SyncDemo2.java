package com.example.sync;

import java.util.concurrent.ConcurrentHashMap;

/**
 * lingfan
 * 2019-09-28 20:27
 */
public class SyncDemo2 {

    Object lock;

    public SyncDemo2(Object lock) {
        this.lock = lock;
    }

    public void demo(){
        synchronized (lock){
            System.out.println("进入代码块");
        }
    }

    public static void main(String[] args) {
        SyncDemo2 syncDemo2 = new SyncDemo2(new Object());
    }
}
