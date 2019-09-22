package com.mybaties.v1;


import java.lang.reflect.Proxy;

/**
 * lingfan
 * 2019-09-20 14:09
 */
public class SqlSession {

    private Configuration configuration;

    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }


    public <T> T getMapper(Class<T> tClass){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),new Class[]{tClass},new MapperProxy(this));

    }

    public <T> T selectOne(String statement,String paramter){
        return executor.selectOne(statement,paramter);
    }
}
