package com.example.spring2.formwork.beans.config;

/**
 * lingfan
 * 2019-08-19 16:20
 */
public class BeanPostProcessor {

    public Object postPorcessorBeforeInitialization(Object bean,String beanName){
        return bean;
    }

    public Object postPorcessorAfterInitialization(Object bean,String beanName){
        return bean;
    }
}
