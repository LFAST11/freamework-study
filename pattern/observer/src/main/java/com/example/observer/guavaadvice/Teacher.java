package com.example.observer.guavaadvice;

import com.example.observer.advice.Question;
import com.google.common.eventbus.Subscribe;

/**
 * lingfan
 * 2019-07-19 17:10
 */
public class Teacher  {

    private String userName;

    public Teacher(String userName) {
        this.userName = userName;
    }

    @Subscribe
    public void update(Question question){
        System.out.println(userName+"：老师您好，您在"+Platform.getInstance().getName()+"收到了来自"+question.getUserName()+"的提问，具体内容如下："+question.getContent());
    }
}
