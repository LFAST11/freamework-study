package com.example.observer.event.core;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-07-19 16:08
 */
@Data
public class Event {

    //事件源
    private Object source;

    //事件触发，要通知谁
    private Object target;

    //事件触发，要做什么动作，回调
    private Method callback;

    //事件的名称。触发的什么事件
    private String trigger;

    //事件的触发时间
    private long time;

    public Event(Object target, Method callback) {
        this.target = target;
        this.callback = callback;
    }
}
