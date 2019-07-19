package com.example.observer.guava;

import com.google.common.eventbus.EventBus;

/**
 * lingfan
 * 2019-07-19 17:01
 */
public class GuavaTest {

    public static void main(String[] args) {
        //消息总线
        EventBus eventBus = new EventBus();

        GuavaEvent guavaEvent = new GuavaEvent();
        //注册事件
        eventBus.register(guavaEvent);
        eventBus.post("天王盖地虎");
    }
}
