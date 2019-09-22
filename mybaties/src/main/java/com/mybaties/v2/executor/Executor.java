package com.mybaties.v2.executor;

import com.mybaties.v2.config.MapperRegistory;

public interface Executor {
//    <T> T selectOne(String statement, String parameter);

    <T> T query(MapperRegistory.MapperData mapperData, Object parameter);

}
