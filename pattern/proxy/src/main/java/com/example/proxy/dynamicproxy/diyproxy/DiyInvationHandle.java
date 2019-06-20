package com.example.proxy.dynamicproxy.diyproxy;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-06-14 17:39
 */
public interface DiyInvationHandle {

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
