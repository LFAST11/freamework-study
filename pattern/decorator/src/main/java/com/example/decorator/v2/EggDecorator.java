package com.example.decorator.v2;

/**
 * lingfan
 * 2019-07-19 14:12
 */
public class EggDecorator extends BatterCakeDecorator {


    public EggDecorator(BatterCake batterCake) {
        super(batterCake);
    }

    @Override
    public String getMsg() {
        return super.getMsg()+"+鸡蛋";
    }

    @Override
    public int getValue() {
        return super.getValue()+2;
    }
}
