package com.example.spring1.v1;

import com.example.spring1.annotation.DiyAutowired;
import com.example.spring1.annotation.DiyController;
import com.example.spring1.annotation.DiyMapper;
import com.example.spring1.annotation.DiyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * lingfan
 * 2019-07-25 14:17
 */
public class DiyDispatcherServlet extends HttpServlet {

    private Map<String,Object> beanMap = new HashMap();


    private Map<String,Object> iocMap = new HashMap();

    private Map<String,Object> handleMap = new HashMap();




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        if(handleMap.containsKey(url)){
            Method method = (Method) handleMap.get(url);
            Map<String,String[]> params = req.getParameterMap();
            try {
                method.invoke(iocMap.get(method.getDeclaringClass().getName()),new Object[]{req,resp,params.get("name")[0]});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            resp.getWriter().write("404 not found");
        }

    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.获取配置中指定的扫描路径
        Properties properties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String packagePath = (String) properties.get("scan.package");

        //加载扫描路径中的所有类
        doScanner(packagePath);

        //扫描注解，放入ioc容器

         for(String className : beanMap.keySet()){

            try {
                Class<?> aClass = Class.forName(className);
                if(aClass.isAnnotationPresent(DiyController.class)){
                    iocMap.put(className,aClass.newInstance());

                    if(aClass.isAnnotationPresent(DiyMapper.class)){
                        DiyMapper annotation = aClass.getAnnotation(DiyMapper.class);
                        String baseUrl = annotation.name();

                        Method[] methods = aClass.getMethods();
                        for (Method method : methods) {
                            if(method.isAnnotationPresent(DiyMapper.class)){
                                DiyMapper methodMapper = method.getAnnotation(DiyMapper.class);
                                String detailUrl = methodMapper.name();
                                String url = (baseUrl+"/"+detailUrl).replaceAll("/+","/");
                                handleMap.put(url,method);
                            }
                        }
                    }
                }else if(aClass.isAnnotationPresent(DiyService.class)){
                    DiyService diyService = aClass.getAnnotation(DiyService.class);
                    String name = diyService.name();
                    Object instance = aClass.newInstance();
                    iocMap.put(className,instance);
                    iocMap.put(name,instance);

                    for (Class<?> interfaceClass : aClass.getInterfaces()){
                        iocMap.put(interfaceClass.getName(),instance);
                    }

                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

         //依赖注入
        for (Object bean  : iocMap.values()){
            Class<?> controllerClass = bean.getClass();
            if(controllerClass.isAnnotationPresent(DiyController.class)){
                Field[] fields = controllerClass.getDeclaredFields();

                for (Field field  : fields){
                    if (field.isAnnotationPresent(DiyAutowired.class)){
                        String name = field.getType().getName();
                        if(iocMap.containsKey(name)){
                            field.setAccessible(true);
                            try {
                                field.set(iocMap.get(controllerClass.getName()), iocMap.get(name));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }


        System.out.println("初始化完成");

    }

    private void doScanner(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + packagePath.replaceAll("\\.", "/"));
        File scanDir = new File(url.getFile());

        if(scanDir.isDirectory()){
            for(File file :scanDir.listFiles()){
                if(file.isDirectory()) {
                    doScanner(packagePath+ "."+file.getName());
                }else{
                    if(! file.getName().endsWith(".class")) continue;
                    String className = packagePath+"."+file.getName().replaceAll(".class","");
                    beanMap.put(className,null);
                }
            }
        }
    }
}
