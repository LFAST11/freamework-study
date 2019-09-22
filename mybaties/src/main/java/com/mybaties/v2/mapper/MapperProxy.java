package com.mybaties.v2.mapper;

import com.mybaties.v2.config.MapperRegistory;
import com.mybaties.v2.session.SqlSession;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-20 14:15
 */
@Data
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperRegistory mapperRegistry = this.sqlSession.getConfiguration().getMapperRegistry();

        MapperRegistory.MapperData mapperData = mapperRegistry.methodSqlMapper.get(method.getName());
        if(mapperData != null){
            System.out.println(String.format("SQL :[%s],PARAM:[%s]",mapperData.getSql(),String.valueOf(args[0])));
            return  sqlSession.selectOne(mapperData,String.valueOf(args[0]));
        }
        return method.invoke(args);
    }
}
