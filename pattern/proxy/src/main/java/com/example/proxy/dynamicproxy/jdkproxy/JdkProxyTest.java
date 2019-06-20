package com.example.proxy.dynamicproxy.jdkproxy;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-06-14 16:32
 */
public class JdkProxyTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        Object proxy = new ZhenAiProxy().getInstance(new Girl());

        Method method = proxy.getClass().getMethod("findLove", null);

        method.invoke(proxy);

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});

        FileOutputStream fos = new FileOutputStream("/Applications/temp/$proxy.class");

        fos.write(bytes);

        fos.flush();

        fos.close();

    }
}
