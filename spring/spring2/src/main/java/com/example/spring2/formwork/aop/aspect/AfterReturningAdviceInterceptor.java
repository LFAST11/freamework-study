package com.example.spring2.formwork.aop.aspect;

import com.example.spring2.formwork.aop.interceptor.MethodInterceptor;
import com.example.spring2.formwork.aop.interceptor.MethodInvocation;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-08 09:35
 */
public class AfterReturningAdviceInterceptor extends AbstractAspectAdvise implements MethodInterceptor {

    private JoinPoint joinPoint;

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviseMethod(this.joinPoint,retVal,null);
    }
}
