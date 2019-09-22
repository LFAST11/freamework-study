package com.mybaties.v2.config;

import com.mybaties.v2.config.annotation.Select;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * lingfan
 * 2019-09-20 14:12
 */
@Data
public class Configuration {

    private String scanPath;

    private List<String> classNameList = new ArrayList<>();

    private MapperRegistory mapperRegistry = new MapperRegistory();

    public Configuration(String scanPath) {
        this.scanPath = scanPath;
    }

    public void build() throws ClassNotFoundException {
        //扫描表所在路径

        URL url = this.getClass().getResource("/"+scanPath.replaceAll("\\.", "/"));

        File scanDir = new File(url.getFile());

        if(scanDir.isDirectory()){
            for (File file : scanDir.listFiles()) {
                classNameList.add(scanPath+"."+file.getName().replace(".class",""));
            }
        }

        //遍历所有类

        for (String className : classNameList) {
            Class<?> aClass = Class.forName(className);

            Method[] methods = aClass.getDeclaredMethods();

            for (Method method : methods) {
                if(method.isAnnotationPresent(Select.class)){
                    Select annotation = method.getAnnotation(Select.class);
                    String[] sql = annotation.value();
                    MapperRegistory.MapperData mapperData = new MapperRegistory.MapperData(sql[0],getActualClass(method));
                    mapperRegistry.methodSqlMapper.put(method.getName(),mapperData);
                }
            }
        }
    }

    public Class getActualClass(Method method){
        Type genericReturnType = method.getGenericReturnType();

        if(genericReturnType instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            return (Class)actualTypeArguments[0];
        }
        return (Class)genericReturnType;
    }
}
