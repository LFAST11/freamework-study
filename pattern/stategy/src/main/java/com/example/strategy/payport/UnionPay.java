package com.example.strategy.payport;

/**
 * lingfan
 * 2019-07-08 16:12
 */
public class UnionPay extends  Payment {
    @Override
    public String getName() {
        return "银联支付";
    }

    @Override
    public double queryBalance(String uid) {
        return 230;
    }
}
