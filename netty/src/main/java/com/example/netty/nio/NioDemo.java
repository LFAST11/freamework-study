package com.example.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * lingfan
 * 2019-10-30 16:07
 */
public class NioDemo {

    private int port = 8080;

    private Selector selector;

    private ByteBuffer buffer = ByteBuffer.allocate(2024);


    public NioDemo(int port) {
        this.port = port;

        try {
            //初始化服务
            ServerSocketChannel server = ServerSocketChannel.open();
            //绑定端口
            server.bind(new InetSocketAddress(this.port));
            //将默认阻塞改成不阻塞
            server.configureBlocking(false);

            //初始化选择器，大堂经理
            selector = Selector.open();

            //绑定选择器
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){

        System.out.println("listen port:"+port);

        //轮询监测
        while (true){

            try {
                //这里相当于大堂经理叫号，目前为止可以进行操作的key
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                //这就是为什么是同步的，每次是一个个去处理不同的key
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    process(selectionKey);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void process(SelectionKey selectionKey) throws IOException {

        //对不同的操作进行不同的处理
        if(selectionKey.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

            SocketChannel channel = server.accept();

            channel.configureBlocking(false);

            //将状态修改为可读
            selectionKey =  channel.register(selector,SelectionKey.OP_READ);
        }
        else if(selectionKey.isReadable()){

            //从多路复用器中拿到客户端的引用
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            int len = channel.read(buffer);

            if(len >0){
                buffer.flip();
                String content = new String(buffer.array(),0,len);
                selectionKey = channel.register(selector,SelectionKey.OP_WRITE);
                //在key增加附件
                selectionKey.attach(content);
                System.out.println("读取内容："+content);

            }
        }
        else if(selectionKey.isWritable()){
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            String content = (String) selectionKey.attachment();

            channel.write(ByteBuffer.wrap(("输出:"+content).getBytes()));

            channel.close();
        }
    }

    public static void main(String[] args) {
        new NioDemo(8080).listen();
    }
}
