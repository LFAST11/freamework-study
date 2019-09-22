package com.mybaties.v2.executor;

import com.mybaties.v2.config.Configuration;

/**
 * lingfan
 * 2019-09-20 17:11
 */
public class ExecutorFatory {

    private static final String SIMPLE = "simple";
    private static final String CACHE = "cache";


    public static  Executor getExecute(String key, Configuration configuration){
        if(key.equals(SIMPLE)){
            return new SimpleExecutor(configuration);
        }else{
            return new CacheExecutor(new SimpleExecutor(configuration));
        }
    }

}
