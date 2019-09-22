package com.mybaties.v2.session;


import com.mybaties.v2.config.Configuration;
import com.mybaties.v2.config.MapperRegistory;
import com.mybaties.v2.executor.Executor;
import com.mybaties.v2.mapper.MapperProxy;
import lombok.Data;


import java.lang.reflect.Proxy;

/**
 * lingfan
 * 2019-09-20 14:09
 */
@Data
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

    public <T> T selectOne(MapperRegistory.MapperData mapperData, Object parameter){
        return executor.query(mapperData,parameter);
    }
}
