package com.example.struct.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lingfan
 * 2019-05-31 15:22
 * spring ioc容器中实现的单例模式
 */
public class ContainerSingleton {

    private final Map<String,Object> ioc = new ConcurrentHashMap();


    private  synchronized Object getBean(String className){
        if(!ioc.containsKey(className)){
            Object obj = null;
            try {
                obj = Class.forName(className).newInstance();
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ioc.get(className);
    }



}
