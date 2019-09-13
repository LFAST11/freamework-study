package com.example.spring2.formwork.aop;

public interface AopProxy {

    Object getProxy();


    Object getProxy(ClassLoader classLoader);
}
