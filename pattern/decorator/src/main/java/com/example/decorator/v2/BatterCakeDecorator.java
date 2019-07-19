package com.example.decorator.v2;

/**
 * lingfan
 * 2019-07-19 14:10
 */
public class BatterCakeDecorator implements BatterCake {

    private BatterCake batterCake;

    public BatterCakeDecorator(BatterCake batterCake) {
        this.batterCake = batterCake;
    }

    public void doSomething(){};
    @Override
    public String getMsg() {
        return batterCake.getMsg();
    }

    @Override
    public int getValue() {
        return batterCake.getValue();
    }
}
