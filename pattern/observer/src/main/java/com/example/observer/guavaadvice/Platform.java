package com.example.observer.guavaadvice;


import com.google.common.eventbus.EventBus;

/**
 * lingfan
 * 2019-07-19 15:17
 */
public class Platform  extends EventBus {

    private String name = "知乎平台";

    private static Platform student = null;

    private Platform(){};

    public String getName() {
        return name;
    }


    public static Platform getInstance(){
        if( student == null) {
            student = new Platform();
        }
        return student;
    }



}
