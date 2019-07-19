package com.example.decorator.v1;

/**
 * lingfan
 * 2019-07-19 11:57
 */
public class BattercakeWithEgg  extends Battercake{

    @Override
    public String getMsg() {
        return super.getMsg()+"+鸡蛋";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+2;
    }
}
