package com.example.spring2.formwork.aop.interceptor;

/**
 * lingfan
 * 2019-09-08 09:27
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
