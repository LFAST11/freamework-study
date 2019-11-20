package com.example.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * lingfan
 * 2019-10-30 15:52
 */
public class BioClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",8080);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello world".getBytes());

            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
