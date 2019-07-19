package com.example.observer.advice;

import java.util.Observable;

/**
 * lingfan
 * 2019-07-19 15:17
 */
public class Platform extends Observable {

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

    public void pushMessage(Question question){
        System.out.println(question.getUserName()+"在"+name+"提出了一个问题,问题详情："+question.getContent());
        setChanged();
        notifyObservers(question);
    }


}
