package com.example.spring2.formwork.beans;

import lombok.Data;

/**
 * lingfan
 * 2019-08-19 16:28
 */
@Data
public class BeanWrapper {

    private Object wrapperInstance;

    private Class<?> wrapperClass;

    public BeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }
}
