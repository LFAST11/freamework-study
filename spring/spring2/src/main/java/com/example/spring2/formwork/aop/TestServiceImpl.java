package com.example.spring2.formwork.aop;

/**
 * lingfan
 * 2019-09-08 12:46
 */
public class TestServiceImpl implements ITestService {
    @Override
    public void sayHello() {
        System.out.println("你怕是个狗吧");
    }
}
