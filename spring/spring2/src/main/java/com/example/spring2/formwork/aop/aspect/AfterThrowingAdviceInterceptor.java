package com.example.spring2.formwork.aop.aspect;

import com.example.spring2.formwork.aop.interceptor.MethodInterceptor;
import com.example.spring2.formwork.aop.interceptor.MethodInvocation;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-08 09:35
 */
public class AfterThrowingAdviceInterceptor extends AbstractAspectAdvise implements MethodInterceptor {
    private String  throwingName;

    public AfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
          return   mi.proceed();
        } catch (Throwable throwable) {
            invokeAdviseMethod(mi,null,throwable);
            throw throwable ;
        }
    }

    public void setThrowingName(String throwingName) {
        this.throwingName = throwingName;
    }
}
