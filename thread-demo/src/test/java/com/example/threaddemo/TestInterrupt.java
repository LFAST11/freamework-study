package com.example.threaddemo;

import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-09-23 20:01
 */
public class TestInterrupt {

    private static int i;

    public static void  test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("当前线程被中断，累计的和为："+i);
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
    }


    public static void  test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("线程执行中。。。");
            }
            System.out.println("线程被中断");
            Thread.interrupted();

            System.out.println("线程中断标示复位，当前标志为:"+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
    }



    public static void  test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {

            while (true) {
                try {
                    System.out.println("线程执行中。。。");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("线程被中断");
                    System.out.println("对于中断之后抛出的异常可以进行相应的处理");
                }
            }
            }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();

    }


    public static void main(String[] args) throws InterruptedException {

        test2();
    }

}
