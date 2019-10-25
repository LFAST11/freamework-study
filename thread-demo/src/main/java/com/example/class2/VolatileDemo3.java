package com.example.class2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * lingfan
 * 2019-10-08 11:35
 */
public class VolatileDemo3 {

    private volatile static AtomicInteger num = new AtomicInteger(0) ;


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    num.getAndIncrement();
                }
                //System.out.println("11111");

            }).start();
        }

        Thread.sleep(2000);
        System.out.println(num.get());
    }
}
