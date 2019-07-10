package com.example.proxy.dynamicproxy.diyproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * lingfan
 * 2019-06-14 17:39
 */
public class DIyZHenAI implements DiyInvationHandle {

    private Object target;

    public Object getInstance(Object target) throws Exception {
        this.target = target;
        Class<?> targetClass = target.getClass();
        return DiyProxy.newProxyInstance(new DiyClassLoader(), targetClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object = method.invoke(target, args);
        after();
        return object;
    }


    public void before() {
        System.out.println("先自我介绍一下吧");
    }

    public void after() {
        System.out.println("交换微信吧");
    }
}
