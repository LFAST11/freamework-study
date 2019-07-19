package com.example.adapter;

/**
 * lingfan
 * 2019-07-16 18:22
 */
public interface Device {

    public boolean support(Object Adapter);

    public void outData(String data,Object Adapter);

    public void inputData(String data,Object Adapter);

}
