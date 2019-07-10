package com.example.strategy.payport;

/**
 * lingfan
 * 2019-07-08 16:12
 */
public class WeXinPay extends Payment{
    @Override
    public String getName() {
        return "微信支付";
    }

    @Override
    public double queryBalance(String uid) {
        return 666;
    }
}
