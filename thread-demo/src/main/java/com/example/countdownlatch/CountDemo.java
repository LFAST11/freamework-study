package com.example.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * lingfan
 * 2019-10-24 10:51
 */
public class CountDemo {

    public static void main(String[] args) throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(3);

        for(int i=0;i<3;i++){
            new Thread(()->{
                System.out.println("子线程执行任务："+Thread.currentThread().getName());
                countDownLatch.countDown();
            },"thread"+i).start();

        }
        countDownLatch.await();


        System.out.println("主线程继续执行任务啦");
    }
}
