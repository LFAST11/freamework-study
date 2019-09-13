package com.example.spring2.formwork.beans.factory;

/**
 * lingfan
 * 2019-09-07 11:28
 */
public class ObjectFactory {


    private Object object;

    public ObjectFactory(Object object) {
        this.object = object;
    }

    public Object getObject(){
        return object;
    }
}
