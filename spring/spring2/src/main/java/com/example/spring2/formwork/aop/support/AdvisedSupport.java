package com.example.spring2.formwork.aop.support;

import com.example.spring2.formwork.aop.aspect.Advise;
import com.example.spring2.formwork.aop.aspect.AfterReturningAdviceInterceptor;
import com.example.spring2.formwork.aop.aspect.AfterThrowingAdviceInterceptor;
import com.example.spring2.formwork.aop.aspect.MethodBeforeAdviceInterceptor;
import com.example.spring2.formwork.aop.config.AopConfig;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lingfan
 * 2019-09-08 09:09
 */
@Data
public class AdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private AopConfig aopConfig;

    private Pattern pointCutClassPattern;

    private transient Map<Method,List<Object>> methodCache;

    public AdvisedSupport() {
    }

    public AdvisedSupport(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }


    //得到所有方法上面的通知拦截
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {

        List<Object> cached = methodCache.get(method);

        if(cached == null){

            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());

            cached = methodCache.get(m);

            this.methodCache.put(m,cached);

        }
        return cached;
    }


    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    public void parse() {

        String pointCut = aopConfig.getPointCut();

        pointCut = pointCut.replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");

        //pointCut=public .* com.example.spring2.demo.service..*Service..*(.*)
        String pointCutClassRegix = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class "+pointCutClassRegix.substring(pointCutClassRegix.lastIndexOf(" ")+1));

        methodCache = new HashMap<>();

        try {
            Pattern pattern = Pattern.compile(pointCut);


            Class<?> aspectClass = Class.forName(this.aopConfig.getAspectClass());

            Map<String,Method> aspectMethod = new HashMap<>();

            for (Method method : aspectClass.getMethods()) {
                aspectMethod.put(method.getName(),method);
            }


            for (Method method : this.getTargetClass().getMethods()) {
                String methodString = method.toString();

                if(methodString.contains("throws")){
                    methodString = methodString.substring(0,methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);

                if(matcher.matches()){


                    //执行器链
                    List<Object> advises = new ArrayList<>();

                    //如果满足条件，就吧方法包装成methodInterceptor

                    if(aopConfig.getAspectBefore() != null && !aopConfig.getAspectBefore().equals("")){
                        advises.add(new MethodBeforeAdviceInterceptor(aspectMethod.get(aopConfig.getAspectBefore()),aspectClass.newInstance()));
                    }

                    if(aopConfig.getAspectAfter() != null && !aopConfig.getAspectAfter().equals("")){
                        advises.add(new AfterReturningAdviceInterceptor(aspectMethod.get(aopConfig.getAspectAfter()),aspectClass.newInstance()));
                    }

                    if(aopConfig.getAspectAfterThrow() != null && !aopConfig.getAspectAfterThrow().equals("")){
                        AfterThrowingAdviceInterceptor afterThrowingAdviceInterceptor = new AfterThrowingAdviceInterceptor(aspectMethod.get(aopConfig.getAspectAfterThrow()), aspectClass.newInstance());
                        afterThrowingAdviceInterceptor.setThrowingName(aopConfig.getAspectAfterThrowingName());
                        advises.add(afterThrowingAdviceInterceptor);
                    }
                    methodCache.put(method,advises);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
