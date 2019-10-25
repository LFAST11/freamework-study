package com.example.semphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-10-24 10:59
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i <10 ; i++) {
            new Car(i+1,semaphore).start();
        }


    }


    static class Car extends Thread{
        private int num;

        private Semaphore semaphore;

        public Car(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//获取令牌资源
                System.out.println("第"+num+"量车抢占车位");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("第"+num+"量车走了");
                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
