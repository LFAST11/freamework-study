package com.example.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lingfan
 * 2019-10-21 09:52
 */
public class ReentrantLockDemo {

     private static int count = 0;

     static Lock lock = new ReentrantLock();

     public static void inc(){

         lock.lock();

         try {
             Thread.sleep(1);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         count++;

         lock.unlock();
     }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <1000 ; i++) {
            new Thread(() ->inc()).start();
        }

        Thread.sleep(3000);

        System.out.println(count);
    }
}
