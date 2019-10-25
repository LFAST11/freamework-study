package com.example.class2;

/**
 * lingfan
 * 2019-10-08 11:35
 */
public class VolatileDemo2 {

    private volatile static  int i = 0 ;

    public static  void increment(){
        i++;
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increment();
                }

            }).start();
        }
        Thread.sleep(2000);
        System.out.println(i);
    }
}
