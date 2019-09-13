package com.example.spring2.formwork.aop;

import com.example.spring2.formwork.aop.support.AdvisedSupport;

/**
 * lingfan
 * 2019-09-08 09:04
 */
public class CglibAopProxy implements AopProxy {
    private AdvisedSupport advisedSupport;

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
