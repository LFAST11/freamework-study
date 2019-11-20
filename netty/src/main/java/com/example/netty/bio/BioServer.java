package com.example.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * lingfan
 * 2019-10-30 15:47
 */
public class BioServer {

    private int port;

    private ServerSocket serverSocket;

    public BioServer(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        while (true){
            try {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();

                byte[] buff = new byte[1024];
                int len = inputStream.read(buff);
                if(len>0){
                    String msg = new String(buff,0,len);
                    System.out.println("收到消息:"+msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new BioServer(8080).listen();
    }
}
