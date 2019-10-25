package com.example.queue;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lingfan
 * 2019-10-25 14:55
 */
public class Queue<E> {

    private Integer size;

    private ArrayList<E> list = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullCondition = lock.newCondition();

    private Condition emptyCondition = lock.newCondition();


    public Queue(Integer size) {
        this.size = size;
    }


    public void put(E v){

        try {
            lock.lock();
            if(list.size() >= size) {
                System.out.println("队列已满，等待..."+v);
                fullCondition.await();
            }
            Thread.sleep(1000);
            list.add(v);
            System.out.println("队列增加元素"+v);

            emptyCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public E take(){
        try {
            lock.lock();
            if(list.size()  == 0){
                System.out.println("目前队列为空，无法获取数据");
                emptyCondition.await();
            }
            Thread.sleep(500);

            E v = list.remove(0);
            System.out.println("队列移除元素"+v);

            fullCondition.signal();
            return v;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new Queue<>(3);



        for (int i = 0; i <10 ; i++) {
            new Thread(()-> queue.take()).start();
        }
        for (int i = 0; i <10 ; i++) {
            int finalI = i;
            new Thread(()->queue.put(finalI)).start();
        }
        Thread.sleep(5000);

    }

}
