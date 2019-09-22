package com.mybaties.v2.executor;

import com.mybaties.v2.config.MapperRegistory;

import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-09-20 17:11
 */
public class CacheExecutor implements Executor{

    private Map<String,Object> localCache = new HashMap<>();

    private Executor delegate;

    public CacheExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T query(MapperRegistory.MapperData mapperData, Object parameter) {
        Object cache = localCache.get(mapperData.getSql());
        if(cache != null){
            System.out.println("命中缓存");
            return (T)cache;
        }else{
            T result = delegate.query(mapperData, parameter);
            localCache.put(mapperData.getSql(),result);
            return result;
        }
    }
}
