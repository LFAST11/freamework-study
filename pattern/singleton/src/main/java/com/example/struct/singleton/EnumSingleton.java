package com.example.struct.singleton;

/**
 * lingfan
 * 2019-05-31 15:32
 * 推荐使用,
 */
public enum  EnumSingleton {
    INSTANCE;

    private Object data;

    EnumSingleton(){ }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static EnumSingleton getInstance(){
        return INSTANCE;
    }


}
