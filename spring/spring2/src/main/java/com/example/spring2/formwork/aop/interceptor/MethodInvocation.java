package com.example.spring2.formwork.aop.interceptor;

import com.example.spring2.formwork.aop.aspect.JoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * lingfan
 * 2019-09-08 09:21
 */
public class MethodInvocation implements JoinPoint {

    private Object proxy;

    private Method method;

    private Object target;

    private Object[] arguments;

    private List<Object> interceptorsAndDynamicInterceptionAdvice;

    private Class<?> targetClass;

    private Map<String, Object> userAttributes;

    private int currentInterceptorIndex = -1;

    public MethodInvocation(Object proxy, Method method, Object target, Object[] arguments, List<Object> interceptorsAndDynamicInterceptionAdvice, Class<?> targetClass) {
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.arguments = arguments;
        this.interceptorsAndDynamicInterceptionAdvice = interceptorsAndDynamicInterceptionAdvice;
        this.targetClass = targetClass;
    }

    public Object proceed() throws Throwable{
        //如果Interceptor执行完了，则执行joinPoint->配置的切面处理方法
        if(interceptorsAndDynamicInterceptionAdvice == null || currentInterceptorIndex == this.interceptorsAndDynamicInterceptionAdvice.size() - 1){
            return this.method.invoke(this.targetClass,this.arguments);
        }


        Object interceptorAdvice = this.interceptorsAndDynamicInterceptionAdvice.get(++currentInterceptorIndex);

        //动态匹配所有joinPoint
        if(interceptorAdvice instanceof MethodInterceptor){
            MethodInterceptor mi = (MethodInterceptor) interceptorAdvice;
            return mi.invoke(this);
        }else{
            //动态匹配失败后，略过当前interceptor，调用下一个Interceptor
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }


    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
