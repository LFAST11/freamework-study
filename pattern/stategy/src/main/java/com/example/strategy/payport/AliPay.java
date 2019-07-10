package com.example.strategy.payport;

/**
 * lingfan
 * 2019-07-08 16:11
 */
public class AliPay extends  Payment {
    @Override
    public String getName() {
        return "阿里支付";
    }

    @Override
    public double queryBalance(String uid) {
        return 700;
    }
}
