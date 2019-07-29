package com.example.spring1.v2;

import com.example.spring1.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * lingfan
 * 2019-07-28 15:26
 */
public class DispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> beanList = new ArrayList();

    private Map<String,Object> iocMap = new HashMap();

    private List<Handle> handleList = new ArrayList<Handle>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req,resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {

        Handle handle = getHandle(req);

        if(handle == null){
            try {
                resp.getWriter().write("404 NOT FOUND");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //填充参数
        Map<String,String[]> parameterMap = req.getParameterMap();

        Class<?>[] parameterTypes = handle.getMethod().getParameterTypes();

        Object[] paramValues = new Object[parameterTypes.length];

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = Arrays.toString(entry.getValue())
                    .replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");

            if(handle.getParamIndexMap().containsKey(entry.getKey())){
                int index = handle.getParamIndexMap().get(entry.getKey());
                paramValues[index] = convert(value,parameterTypes[index]);
            }
        }

        //设置request和response对象
        Integer requestIndex = handle.getParamIndexMap().get(HttpServletRequest.class.getName());
        if(requestIndex != null){
            paramValues[requestIndex] = req;
        }

        Integer responseIndex = handle.getParamIndexMap().get(HttpServletResponse.class.getName());

        if(responseIndex != null){
            paramValues[responseIndex] = resp;
        }

        try {
            handle.getMethod().invoke(handle.getController(),paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private Object convert(String value, Class<?> type) {
        if(type == Integer.class){
            return Integer.valueOf(value);
        }
        if(type == Double.class){
            return Double.valueOf(value);
        }
        if(type == int.class){
            return Integer.valueOf(value);
        }
        if(type == double.class){
            return Double.valueOf(value);
        }
        return value;
    }

    private Handle getHandle(HttpServletRequest request) {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        for (Handle handle : handleList) {
            Matcher matcher = handle.pattern.matcher(url);
            if(matcher.matches()){
                return handle;
            }
        }

        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1。初始化参数
        initProperty(config.getInitParameter("contextConfigLocation"));

        //2.扫描bean
        scannerBean(properties.getProperty("scan.package"));

        //3.初始化ioc容器
        initIocContainer();

        //4.注入属性
        doAutoWired();

        //5.构造handleMapper
        initHandleMapper();
    }

    private void initHandleMapper() {
        for (Object value : iocMap.values()) {
            Class<?> aClass = value.getClass();
            if(aClass.isAnnotationPresent(DiyController.class)){

                String baseUrl = null;
                if(aClass.isAnnotationPresent(DiyMapper.class)){
                    DiyMapper annotation = aClass.getAnnotation(DiyMapper.class);
                    baseUrl = annotation.name();
                }

                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(DiyMapper.class)){
                        DiyMapper methodMapper = method.getAnnotation(DiyMapper.class);
                        String detailUrl = methodMapper.name();
                        String regex = (baseUrl+"/"+detailUrl).replaceAll("/+","/");
                        Pattern pattern = Pattern.compile(regex);
                        handleList.add(new Handle(value,method,pattern));
                        System.out.println("mappering "+regex+" method"+method);
                    }
                }
            }
        }
    }

    private void doAutoWired() {
        for (Object bean : iocMap.values()) {
            Class<?> beanClass = bean.getClass();
            if(beanClass.isAnnotationPresent(DiyController.class)){
                Field[] fields = beanClass.getDeclaredFields();

                for (Field field  : fields){
                    if (field.isAnnotationPresent(DiyAutowired.class)){
                        String name = field.getType().getName();
                        if(iocMap.containsKey(name)){
                            field.setAccessible(true);
                            try {
                                field.set(iocMap.get(beanClass.getName()), iocMap.get(name));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void initIocContainer() {
        for (String className : beanList) {
            try {
                Class<?> aClass = Class.forName(className);
                String defaultName =toLowerCase(aClass.getSimpleName());
                Object instance = aClass.newInstance();
                if(aClass.isAnnotationPresent(DiyController.class)){
                    iocMap.put(defaultName, instance);
                    iocMap.put(aClass.getName(), instance);


                }else if(aClass.isAnnotationPresent(DiyService.class)){
                    DiyService diyService = aClass.getAnnotation(DiyService.class);
                    String name = diyService.name();
                    iocMap.put(defaultName,instance);
                    iocMap.put(name,instance);
                    iocMap.put(aClass.getName(), instance);

                    for (Class<?> interfaceClass : aClass.getInterfaces()){
                        iocMap.put(interfaceClass.getName(),instance);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String toLowerCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    private void scannerBean(String scanPath) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPath.replaceAll("\\.", "/"));
        File scanDir = new File(url.getFile());

        if(scanDir.isDirectory()){
            for(File file :scanDir.listFiles()){
                if(file.isDirectory()) {
                    scannerBean(scanPath+ "."+file.getName());
                }else{
                    if(! file.getName().endsWith(".class")) continue;
                    String className = scanPath+"."+file.getName().replaceAll(".class","");
                    beanList.add(className);
                }
            }
        }
    }


    private void initProperty(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null ){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private  class Handle{
        private Object controller;
        private Method method;
        private Pattern pattern;
        private Map<String,Integer> paramIndexMap;

        public Handle(Object controller, Method method, Pattern pattern) {
            this.controller = controller;
            this.method = method;
            this.pattern = pattern;
            paramIndexMap = new HashMap<String, Integer>();

            initParamIndexMap(method);
        }

        private void initParamIndexMap(Method method) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof DiyRequestParams) {
                        String name = ((DiyRequestParams) annotation).name();
                        paramIndexMap.put(name,i);
                    }
                }
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if(parameterTypes[i] == HttpServletRequest.class
                        || parameterTypes[i] == HttpServletResponse.class){
                    paramIndexMap.put(parameterTypes[i].getName(),i);

                }
            }

        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        public Map<String, Integer> getParamIndexMap() {
            return paramIndexMap;
        }

        public void setParamIndexMap(Map<String, Integer> paramIndexMap) {
            this.paramIndexMap = paramIndexMap;
        }
    }
}
