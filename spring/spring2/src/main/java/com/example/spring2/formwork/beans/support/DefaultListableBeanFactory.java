package com.example.spring2.formwork.beans.support;

import com.example.spring2.formwork.beans.config.BeanDefinition;
import com.example.spring2.formwork.context.support.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lingfan
 * 2019-08-19 15:19
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    protected  final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

}
