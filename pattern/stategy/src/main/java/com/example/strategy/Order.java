package com.example.strategy;

import com.example.strategy.payport.PayStrategy;
import com.example.strategy.payport.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lingfan
 * 2019-07-08 16:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    public String uId;
    private String orderId;
    private double amount;




    public MsgResult pay(String payKey){
        Payment payment = PayStrategy.getStrategy(payKey);
        System.out.println("欢迎使用："+payment.getName());
        System.out.println("本次交易金额为："+amount+",现在开始扣款...");
        return payment.pay(uId,amount);
    }

}
