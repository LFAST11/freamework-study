package com.example.observer.advice;

import java.util.Observable;
import java.util.Observer;

/**
 * lingfan
 * 2019-07-19 15:29
 */
public class Teacher implements Observer {

    private String userName;

    public Teacher(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform platform = (Platform) o;
        Question question = (Question) arg;

        System.out.println(userName+"：老师您好，您在"+platform.getName()+"收到了来自"+question.getUserName()+"的提问，具体内容如下："+question.getContent());
    }
}
