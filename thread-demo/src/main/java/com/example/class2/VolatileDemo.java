package com.example.class2;

import sun.jvm.hotspot.jdi.ThreadReferenceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-10-05 19:41
 */
public class VolatileDemo {

    private volatile static  Boolean flag = false;


    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(() -> {
            while (true) {
                if (flag) {
                    System.out.println("线程：" + Thread.currentThread().getName());
                    break;
                }
            }
        }, "threadA");
        threadA.start();

       Thread.sleep(1000
       );

        Thread threadB = new Thread(() -> {
            System.out.println("线程：" + Thread.currentThread().getName());
            flag = true;
        }, "ThreadB");
        threadB.start();

        threadA.join();
        threadB.join();

    }
}
