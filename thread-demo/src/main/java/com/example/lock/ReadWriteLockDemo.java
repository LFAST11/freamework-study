package com.example.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * lingfan
 * 2019-10-21 10:13
 */
public class ReadWriteLockDemo {

    public Map<String,Object>  cacheMap = new HashMap<>();

    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Lock read = readWriteLock.readLock();

    static Lock writeLock = readWriteLock.writeLock();


    public  Object get(String key){
        read.lock();
        try {
            return cacheMap.get(key);
        } finally {
            read.unlock();
        }
    }


    public void put(String key,String value){
        writeLock.lock();
        try {
            cacheMap.put(key,value);
        } finally {
            writeLock.unlock();
        }
    }

}
