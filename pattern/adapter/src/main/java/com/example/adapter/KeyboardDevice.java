package com.example.adapter;

/**
 * lingfan
 * 2019-07-16 18:27
 */
public class KeyboardDevice implements Device {
    @Override
    public boolean support(Object Adapter) {
        return Adapter instanceof KeyboardDevice;
    }

    @Override
    public void outData(String data, Object Adapter) {
        System.out.println("输出数据"+data);

    }

    @Override
    public void inputData(String data, Object Adapter) {
        System.out.println("输入数据"+data);

    }
}
