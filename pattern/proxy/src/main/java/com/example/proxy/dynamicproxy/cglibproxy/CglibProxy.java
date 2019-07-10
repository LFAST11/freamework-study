package com.example.proxy.dynamicproxy.cglibproxy;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-06-20 21:04
 */
public class CglibProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) {
        //代理工具类
        Enhancer eh = new Enhancer();
        eh.setSuperclass(clazz);
        eh.setCallback(this);
        return eh.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object Obj = methodProxy.invokeSuper(o, objects);
        after();
        return Obj;
    }

    public void before() {
        System.out.println("进来之前要洗手");
    }

    public void after() {
        System.out.println("出去之后要擦鞋");
    }

}
