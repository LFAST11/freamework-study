package com.example.strategy.payport;

import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-07-08 16:13
 */
public class PayStrategy {


    public static final String alipay = "AliPay";
    public static final String weXin = "WeXinPay";
    public static final String union = "UnionPay";
    public static final String defalult = "AliPay";

    private static Map<String,Payment> paymentMap = new HashMap<>();

    static {
        paymentMap.put(alipay,new AliPay());
        paymentMap.put(weXin,new WeXinPay());
        paymentMap.put(union,new UnionPay());
    }

    public static Payment getStrategy(String payKey){
        return paymentMap.getOrDefault(payKey,paymentMap.get(defalult));
    }
}
