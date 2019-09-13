package com.example.spring2.formwork.aop;

import com.example.spring2.demo.service.impl.QueryService;
import com.example.spring2.formwork.aop.interceptor.MethodInvocation;
import com.example.spring2.formwork.aop.support.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * lingfan
 * 2019-09-08 09:03
 */
public class JdkDynamicAopProxy  implements AopProxy, InvocationHandler {

    private AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return this.getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        try {
            return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
        } catch (Throwable  e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            System.out.println(method.toString());
            List<Object> interceptorsAndDynamicInterceptionAdvice = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
            MethodInvocation methodInvocation = new MethodInvocation(proxy, method, this.advised.getTarget(), args, interceptorsAndDynamicInterceptionAdvice, this.advised.getTargetClass());
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetClass(TestServiceImpl.class);

        try {
            JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
            Object proxy = jdkDynamicAopProxy.getProxy(jdkDynamicAopProxy.advised.getTargetClass().getClassLoader());
            System.out.println(proxy);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
