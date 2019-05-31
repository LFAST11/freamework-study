package com.example.struct.singleton;

/**
 * lingfan
 * 2019-05-31 16:33
 */
public class ThreadLocalSingleton {

    private ThreadLocalSingleton(){

    }

    private static ThreadLocal<ThreadLocalSingleton> threadLocalInstance = new ThreadLocal(){
        @Override
        protected  ThreadLocalSingleton initialValue(){
            return new ThreadLocalSingleton();
        }
    };

    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get();
    }
}
