package com.example.condition;

import sun.jvm.hotspot.debugger.ThreadAccess;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lingfan
 * 2019-10-23 15:20
 */
public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        ConditionWait wait = new ConditionWait(lock,condition);
        ConditionSignal signal = new ConditionSignal(lock,condition);

        new Thread(wait).start();
        new Thread(signal).start();

        Thread.sleep(3000);
    }
}
