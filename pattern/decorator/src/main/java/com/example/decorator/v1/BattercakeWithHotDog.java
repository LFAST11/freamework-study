package com.example.decorator.v1;

/**
 * lingfan
 * 2019-07-19 11:58
 */
public class BattercakeWithHotDog  extends  Battercake{

    @Override
    public String getMsg() {
        return super.getMsg()+"+火腿";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+1;
    }
}
