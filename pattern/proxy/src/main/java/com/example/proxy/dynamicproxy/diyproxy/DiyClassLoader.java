package com.example.proxy.dynamicproxy.diyproxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * lingfan
 * 2019-06-14 17:38
 */
public  class DiyClassLoader  extends  ClassLoader{

    private File classPathFile;

    public  DiyClassLoader(){
        String classPath = DiyClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = DiyClassLoader.class.getPackage().getName() + "." + name;

        if (classPathFile != null){
            File file = new File(classPathFile,name.replace("\\,","/") + ".class");

            if(file.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream bios = null;
                try{
                    fis  = new FileInputStream(file);
                    bios = new ByteArrayOutputStream();

                    byte[] bytes = new byte[1024];
                    int len;
                    while((len = fis.read(bytes)) !=-1){
                        bios.write(bytes,0,len);
                    }

                    return defineClass(className,bios.toByteArray(),0,bios.size());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }
}
