package com.example.decorator.v2;

/**
 * lingfan
 * 2019-07-19 14:14
 */
public class HotDogDecorator extends BatterCakeDecorator {
    public HotDogDecorator(BatterCake batterCake) {
        super(batterCake);
    }


    @Override
    public String getMsg() {
        return super.getMsg()+"+火腿";
    }

    @Override
    public int getValue() {
        return super.getValue()+1;
    }
}
