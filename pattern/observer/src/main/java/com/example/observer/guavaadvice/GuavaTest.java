package com.example.observer.guavaadvice;

import com.example.observer.advice.Question;

/**
 * lingfan
 * 2019-07-19 17:17
 */
public class GuavaTest  {

    public static void main(String[] args) {

        Platform platform = Platform.getInstance();

        Teacher t1 = new Teacher("zhangsna");
        Teacher t2 = new Teacher("lisi");

        Question question = new Question();
        question.setUserName("小米");
        question.setContent("我是你爸爸");


        platform.register(t1);
        platform.register(t2);

        platform.post(question);
    }
}
