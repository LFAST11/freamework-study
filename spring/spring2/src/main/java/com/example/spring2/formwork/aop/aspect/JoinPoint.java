package com.example.spring2.formwork.aop.aspect;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-08 09:38
 */
public interface JoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
