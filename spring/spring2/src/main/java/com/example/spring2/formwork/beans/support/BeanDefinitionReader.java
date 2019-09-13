package com.example.spring2.formwork.beans.support;

import com.example.spring2.formwork.beans.config.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * lingfan
 * 2019-08-19 15:22
 */
public class BeanDefinitionReader {

    private Properties config = new Properties();

    private final String SCAN_PACKAGE = "scan.package";

    private List<String> beanClassList = new ArrayList<String>();

    public BeanDefinitionReader(String ... configLocations){
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(configLocations[0].replace("classpath:",""));

        try {
            config.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(resource != null){
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        //反射获取所有的类

        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));

        File path = new File(url.getFile());

        for (File file : path.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(file.getName().equals(".class")) continue;
                String className = scanPackage+"."+file.getName().replaceAll(".class","");
                beanClassList.add(className);
            }
        }


    }


    public List<BeanDefinition> loadBeanDefinitions(){
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

        for (String clazzName : beanClassList) {
            try {
                Class<?> clazz = Class.forName(clazzName);

                //如果是接口,不需要初始化
                if (clazz.isInterface()) continue;

                //按照类名注入
                beanDefinitions.add(doCreateBeanDefinition(toLowerFirstCase(clazz.getSimpleName()),clazz.getName()));

                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    //按照接口类型注入
                    beanDefinitions.add(doCreateBeanDefinition(anInterface.getName(),clazz.getName()));

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return beanDefinitions;

    }

    public BeanDefinition doCreateBeanDefinition(String factoryBeanName, String className){
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(className);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }


    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }
}
