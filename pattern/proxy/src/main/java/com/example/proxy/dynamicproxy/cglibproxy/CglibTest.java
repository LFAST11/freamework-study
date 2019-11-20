package com.example.proxy.dynamicproxy.cglibproxy;

import org.springframework.aop.framework.AopProxy;

/**
 * lingfan
 * 2019-06-20 21:09
 */
public class CglibTest {

    public static void main(String[] args) {
        People object = (People) new CglibProxy().getInstance(People.class);
        object.findLove();

    }
}
