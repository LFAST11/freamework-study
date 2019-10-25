package com.example.condition;

import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * lingfan
 * 2019-10-23 15:18
 */
public class ConditionSignal implements Runnable{

    private Lock lock;

    private Condition condition;

    public ConditionSignal(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        System.out.println("begin condition-signal");

        try {
            lock.lock();
            condition.signal();
            System.out.println("end condition-signal");
        } finally {
            lock.unlock();
        }
    }
}
