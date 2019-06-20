package com.example.proxy.dynamicproxy.jdkproxy;

/**
 * lingfan
 * 2019-06-14 16:21
 */
public class Girl implements Person {
    @Override
    public void findLove() {
        System.out.println("我要找对象");
        System.out.println("长得帅的");
    }

    @Override
    public Object fallInLove(Object boy, Object girl) {
        return null;
    }
}
