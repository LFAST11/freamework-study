package com.example.cyclicbarrier;

import sun.jvm.hotspot.debugger.ThreadAccess;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * lingfan
 * 2019-10-24 11:14
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new DataCleanThread());

        for (int i =0 ;i<3;i++){
            new DataInputThread(cyclicBarrier).start();
        }


        TimeUnit.SECONDS.sleep(3);
    }
}
