package com.example.netty.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * lingfan
 * 2019-11-20 21:31
 */
public class Request {

    private ChannelHandlerContext ctx;

    private HttpRequest request;

    public Request(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.ctx = ctx;
        this.request = httpRequest;
    }

    public String getUrl() {
        return request.uri();
    }

    public  String getMethod(){
        return request.getMethod().name();
    }
}
