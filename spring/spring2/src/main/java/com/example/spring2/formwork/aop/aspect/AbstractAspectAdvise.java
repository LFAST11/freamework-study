package com.example.spring2.formwork.aop.aspect;

import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-08 09:39
 */
public abstract class AbstractAspectAdvise implements Advise {

    private Method aspectMethod;

    private Object aspectTarget;


    public AbstractAspectAdvise(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }


    public Object invokeAdviseMethod(JoinPoint joinPoint, Object returnVal, Throwable throwable) throws Throwable{
        Class<?>[] parameterTypes = this.aspectMethod.getParameterTypes();

        if(parameterTypes == null || parameterTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if(parameterType ==  JoinPoint.class){
                    args[i] = joinPoint;
                }else if(parameterType == Throwable.class){
                    args[i] = throwable;
                }else if (parameterType == Object.class){
                    args[i] = returnVal;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
