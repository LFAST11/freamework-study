package com.example.decorator.v2;

import com.example.decorator.v1.Battercake;

/**
 * lingfan
 * 2019-07-19 14:06
 */
public class BaseBatterCake  implements BatterCake {
    @Override
    public String getMsg() {
        return "煎饼";
    }

    @Override
    public int getValue() {
        return 5;
    }
}
