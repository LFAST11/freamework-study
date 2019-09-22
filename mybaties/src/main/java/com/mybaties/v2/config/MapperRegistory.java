package com.mybaties.v2.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-09-20 16:04
 */
public class MapperRegistory {

    public static final Map<String,MapperData> methodSqlMapper = new HashMap<>();


    @Data
    public static class MapperData<T>{
        private String sql;

        private Class<T> aClass;

        public MapperData(String sql, Class<T> aClass) {
            this.sql = sql;
            this.aClass = aClass;
        }

    }
}
