package com.mybaties.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-09-20 14:15
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getDeclaringClass().getName().equals("com.mybaties.v1.TestMapper")){
            if(XmlConfiguration.methodCache.containsKey(method.getName())){
               return  sqlSession.selectOne(XmlConfiguration.methodCache.get(method.getName()),String.valueOf(args[0]));
            }
        }

        return method.invoke(args);
    }
}
