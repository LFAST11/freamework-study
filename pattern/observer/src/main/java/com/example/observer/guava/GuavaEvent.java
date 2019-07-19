package com.example.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 * lingfan
 * 2019-07-19 17:00
 */
public class GuavaEvent {

    @Subscribe
    public void subcribe(String str){
        System.out.println("执行subcribe 方法，传入的参数是:"+str);
    }
}
