package com.example.strategy.payport;

import com.example.strategy.MsgResult;

/**
 * lingfan
 * 2019-07-08 16:03
 */
public abstract class Payment {

    public abstract String getName();


    public abstract  double queryBalance(String uid);


    public MsgResult pay(String uid, double amonut){
        if(queryBalance(uid) < amonut){
            return new MsgResult(500,"支付失败","余额不足");
        }
        return new MsgResult(200,"支付成功","支付金额："+amonut);

    }
}
