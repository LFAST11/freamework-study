package com.example.spring2.formwork.beans.config;

import lombok.Data;

/**
 * lingfan
 * 2019-08-19 15:30
 */
@Data
public class BeanDefinition {

    private String factoryBeanName;

    private String className;

    private boolean lazyInit;

    private boolean isSingleton = true;
}
