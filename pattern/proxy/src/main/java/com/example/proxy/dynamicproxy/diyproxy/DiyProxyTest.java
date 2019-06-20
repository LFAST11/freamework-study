package com.example.proxy.dynamicproxy.diyproxy;

import com.example.proxy.dynamicproxy.jdkproxy.Girl;
import com.example.proxy.dynamicproxy.jdkproxy.Person;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-06-17 10:49
 */

public class DiyProxyTest {

    public static void main(String[] args) throws Exception {
        Person person = (Person) new DIyZHenAI().getInstance(new Girl());

        System.out.println(person.getClass());
        person.findLove();


    }
}
