package com.example.observer.event;

import com.example.observer.event.core.Event;

/**
 * lingfan
 * 2019-07-19 16:39
 */
public class MouseEventCallback {

    public void onClick(Event e){
        System.out.println("======================调用点击事件回调==========================");
    }

    public void onDoubleClick(Event e){
        System.out.println("======================调用双击事件回调==========================");
    }

    public void onUp(Event e){
        System.out.println("======================调用弹起事件回调==========================");
    }

    public void onDown(Event e){
        System.out.println("======================调用按下事件回调==========================");
    }

    public void onOver(Event e){
        System.out.println("======================调用离开事件回调==========================");
    }

}
