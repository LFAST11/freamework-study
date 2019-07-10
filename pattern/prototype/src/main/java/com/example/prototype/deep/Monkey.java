package com.example.prototype.deep;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
 * lingfan
 * 2019-06-12 16:27
 */
@Setter
@Getter
public class Monkey implements Cloneable, Serializable {

    private Bangzi bangzi;

    private JInGuZhou jInGuZhou;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public Monkey deepClone() {
        //将二进制对象输入到磁盘中，再将文件加载到内存成为一个新对象
        try {
            OutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(this);

            InputStream is = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
            ObjectInputStream ois = new ObjectInputStream(is);
            Monkey monkey = (Monkey) ois.readObject();
            return monkey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
