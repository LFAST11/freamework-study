package com.example.delegate.mvc;

import com.example.delegate.mvc.controller.MemberController;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-07-08 15:18
 */
public class DispatcherServlet  extends HttpServlet {


    private Map<String,Handle> handleMap = new HashMap<>();




    @Override
    public void init() throws ServletException {
        Class<?> memberClass = MemberController.class;

        try {
            handleMap.put("/web/getMemberById.json",new Handle()
                    .builder()
                    .controller(memberClass)
                    .method(memberClass.getMethod("getMemberById",new Class[]{String.class}))
                    .url("/web/getMemberById.json").build());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //获取用户请求地址
        String url = req.getRequestURI();

        //通过url找到对应的处理策略
        Handle handle = handleMap.get(url);


        //委派处理器执行对应的代码 ，通过反射调用
        Object obj;
        try {
            obj = handle.getMethod().invoke(handle.getController(),req.getParameter("mid"));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //获取执行结果，然后返回给response

    }


    @Data
    @Builder
    @NoArgsConstructor
    class Handle{
        private Object controller;
        private Method method;
        private String url;
    }
}

