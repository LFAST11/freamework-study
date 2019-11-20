package com.example.netty.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.UnsupportedEncodingException;

/**
 * lingfan
 * 2019-11-20 21:32
 */
public class Response {

    private ChannelHandlerContext ctx;

    private HttpRequest request;

    public Response(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.ctx = ctx;
        this.request = httpRequest;
    }


    public void write(String out) throws Exception {

        try {
            if(out ==null || out.length() == 0){
                return ;
            }

            //设置http协议和请求头信息
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8"))
            );

            response.headers().set("Content-Type","text/html");

            ctx.write(response);
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
