package com.example.observer.advice;

/**
 * lingfan
 * 2019-07-19 15:35
 */
public class ObserverTest {

    public static void main(String[] args) {
        Platform  platform = Platform.getInstance();

        Teacher teacher = new Teacher("波妞");
        Teacher teacher2 = new Teacher("海王");


        Question question = new Question();
        question.setUserName("宗介");
        question.setContent("你生气了吗");


        //先绑定订阅者，在消费消息
        platform.addObserver(teacher);
        platform.addObserver(teacher2);
        platform.pushMessage(question);


    }
}
