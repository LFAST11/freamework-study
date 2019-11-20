package com.example.netty.tomcat.http;

/**
 * lingfan
 * 2019-11-20 21:27
 */
public abstract class Servlet {

    public void service(Request request,Response response)throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else {
            doPost(request,response);
        }

    }

    protected abstract void doPost(Request request, Response response)throws Exception;

    protected abstract void doGet(Request request, Response response)throws Exception;
}
