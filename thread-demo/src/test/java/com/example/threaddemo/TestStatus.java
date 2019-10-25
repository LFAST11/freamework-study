package com.example.threaddemo;

import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-09-23 20:01
 */
public class TestStatus {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();


        new Thread(() -> {
            while (true){
                    synchronized (TestStatus.class){
                        try {
                            //调用wait方法必须处于同步代码块中
                            TestStatus.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

            }
        },"t2").start();

        new Thread(new BlockDemo(),"blocked-1").start();
        new Thread(new BlockDemo(),"blocked-2").start();
    }


    static class BlockDemo extends Thread{
        @Override
        public void run() {
            synchronized (BlockDemo.class){
                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
