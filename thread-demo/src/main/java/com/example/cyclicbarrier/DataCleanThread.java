package com.example.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * lingfan
 * 2019-10-24 11:16
 */
public class DataCleanThread extends Thread {

    @Override
    public void run() {
        System.out.println("等待其他线程全部传输数据完毕，开始清理数据");

    }
}
