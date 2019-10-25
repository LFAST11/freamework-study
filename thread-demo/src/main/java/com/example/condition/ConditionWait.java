package com.example.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * lingfan
 * 2019-10-23 15:16
 */
public class ConditionWait implements Runnable{

    private Lock lock;

    private Condition condition;

    public ConditionWait(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        System.out.println("begin condition-wait");
        try {
            lock.lock();
            condition.await();
            System.out.println("end condition-wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
