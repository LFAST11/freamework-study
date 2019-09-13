package com.example.spring2.formwork.aop.aspect;

import com.example.spring2.formwork.aop.interceptor.MethodInterceptor;
import com.example.spring2.formwork.aop.interceptor.MethodInvocation;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-08 09:35
 */
public class MethodBeforeAdviceInterceptor extends AbstractAspectAdvise implements MethodInterceptor {
    private JoinPoint joinPoint;

    public MethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void before(Method method,Object[] args,Object target) throws Throwable {
        super.invokeAdviseMethod(joinPoint,null,null);
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {

        this.joinPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return mi.proceed();
    }
}
