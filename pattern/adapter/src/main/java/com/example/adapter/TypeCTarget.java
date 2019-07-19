package com.example.adapter;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;

/**
 * lingfan
 * 2019-07-16 18:19
 */
public interface TypeCTarget {

    /**
     * typec接口，输入数据
     */
    void inputData(Device device,String data);



    /**
     * typec接口，输出数据
     */
    void outputData(Device device,String data);
}
