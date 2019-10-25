package com.example.sync;

import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-09-28 18:01
 */
public class SyncDemo {

    public synchronized   void  method1(){
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("当前线程："+Thread.currentThread().getName()+" 开始工作。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public  void  method2(){
        synchronized (this){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("当前线程："+Thread.currentThread().getName()+" 开始工作。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static  synchronized  void method3(){
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("当前线程："+Thread.currentThread().getName()+" 开始工作。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public  void method4(){
        synchronized(SyncDemo.class){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("当前线程："+Thread.currentThread().getName()+" 开始工作。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public static void main(String[] args) {
        SyncDemo syncDemo = new SyncDemo();

//        new Thread(() -> syncDemo.method1(),"t1").start();
//        new Thread(() -> syncDemo.method1(),"t2").start();
//
//
//
//        SyncDemo syncDemo2 = new SyncDemo();
//
//        new Thread(() -> syncDemo.method1(),"t1").start();
//        new Thread(() -> syncDemo2.method1(),"t2").start();



        new Thread(() -> SyncDemo.method3(),"t1").start();
        new Thread(() -> syncDemo.method4(),"t2").start();



    }
}
