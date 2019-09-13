package com.example.spring2.formwork.webmvc.servlet;

import com.example.spring2.formwork.annotation.DiyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.api.scripting.ScriptUtils.convert;

/**
 * lingfan
 * 2019-08-26 14:20
 */
public class HandleAdapter {

    public boolean supports(Object handle) {return handle instanceof HandleMapper;}


    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response,Object handle) throws Exception {

        HandleMapper handleMapper = (HandleMapper) handle;

        //获取方法中的形参
        Map<String,Integer> paramIndexMap = new HashMap<String, Integer>();

        Annotation[][] parameterAnnotations = handleMapper.getMethod().getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if(annotation instanceof  DiyRequestParam){
                    DiyRequestParam requestParam = (DiyRequestParam) annotation;
                    String value = requestParam.value();
                    paramIndexMap.put(value,i);
                }
            }
        }
        Class<?>[] parameterTypes = handleMapper.getMethod().getParameterTypes();

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class){
                paramIndexMap.put(parameterType.getName(),i);
            }
        }

        //填充参数
        Map<String,String[]> parameterMap = request.getParameterMap();

        Object[] paramValues = new Object[parameterTypes.length];

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = Arrays.toString(entry.getValue())
                    .replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");

            if(paramIndexMap.containsKey(entry.getKey())){
                int index =paramIndexMap.get(entry.getKey());
                paramValues[index] = convert(value,parameterTypes[index]);
            }
        }

        //设置request和response对象
        Integer requestIndex = paramIndexMap.get(HttpServletRequest.class.getName());
        if(requestIndex != null){
            paramValues[requestIndex] = request;
        }

        Integer responseIndex = paramIndexMap.get(HttpServletResponse.class.getName());

        if(responseIndex != null){
            paramValues[responseIndex] = response;
        }

        Object result  = handleMapper.getMethod().invoke(handleMapper.getController(), paramValues);

        if(result == null || result instanceof Void){ return null; }

        if(handleMapper.getMethod().getReturnType() == ModelAndView.class){
            return (ModelAndView)result;
        }
        return null;
    }
}
