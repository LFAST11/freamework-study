package com.example.netty.tomcat;

import com.example.netty.tomcat.http.Request;
import com.example.netty.tomcat.http.Response;
import com.example.netty.tomcat.http.Servlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * lingfan
 * 2019-11-20 21:32
 */
public class Tomcat {

    private Map<String, Servlet> mapper = new HashMap<>();

    private Properties properties = new Properties();

    private int port = 8080;

    public void init() throws Exception {
        //加载资源
        String path = this.getClass().getResource("/").getPath();

        FileInputStream fis = new FileInputStream(path + "web.properties");

        properties.load(fis);

        for (Object value : properties.keySet()) {
            String key = (String) value;
            if(key.endsWith(".url")){
                String servletName = key.replaceAll("\\.url$","");
                String url = properties.getProperty(key);
                String className = properties.getProperty(servletName+".className");
                Servlet servlet =(Servlet) Class.forName(className).newInstance();
                mapper.put(url,servlet);
            }
        }
    }

    public void start() throws Exception {
        init();

        //nio reactor模型，Boss，worker
        //Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //work线程
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            //server 服务
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossGroup,workGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //子线程处理类handle
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //客户端初始化处理
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {

                            //Netty对HTTP协议的封装，顺序有要求
                            // HttpResponseEncoder 编码器
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // HttpRequestDecoder 解码器
                            client.pipeline().addLast(new HttpRequestDecoder());
                            //业务逻辑处理
                            client.pipeline().addLast(new TomcatHandle());
                        }
                    })
                    //主线层配置，分配线程的最大数量为128
                    .option(ChannelOption.SO_BACKLOG,128)
                    //子线程配置，保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            //启动服务器
            ChannelFuture future = server.bind(port);
            System.out.println("tomcat 启动 监听端口:"+port);
            future.channel().closeFuture().sync();
;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


    public class TomcatHandle extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof HttpRequest){
                HttpRequest httpRequest = (HttpRequest) msg;

                Request request = new Request(ctx,httpRequest);

                Response response = new Response(ctx,httpRequest);

                String url = request.getUrl();

                if(mapper.containsKey(url)){
                    mapper.get(url).service(request,response);
                }else{
                    response.write("404-NOT  FOUND");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Tomcat().start();
    }

}
