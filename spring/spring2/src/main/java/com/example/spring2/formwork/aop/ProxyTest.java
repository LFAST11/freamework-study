package com.example.spring2.formwork.aop;

import com.example.spring2.demo.service.IQueryService;
import com.example.spring2.demo.service.impl.QueryService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * lingfan
 * 2019-09-08 22:48
 */
public class ProxyTest implements InvocationHandler {

    private IQueryService queryService;

    public Object getInstance(IQueryService service) {
        this.queryService = service;
        Class<?> targetClass = service.getClass();
        return Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.toString());
        Object object = method.invoke(queryService, args);
        return object;
    }


    public static void main(String[] args) {
        Object instance = new ProxyTest().getInstance(new QueryService());

    }
}
