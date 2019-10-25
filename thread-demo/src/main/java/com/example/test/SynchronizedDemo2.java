package com.example.test;

import java.io.IOException;

/**
 * lingfan
 * 2019-10-25 14:34
 */
public class SynchronizedDemo2 {

     Integer count=0;
    public  void incr(){
        synchronized (count) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        for(int i=0;i<1000;i++){
            new Thread(()->synchronizedDemo.incr()).start();
        }
        Thread.sleep(5000);
        System.out.println("result:"+synchronizedDemo.count);
    }
}
