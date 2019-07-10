package com.example.strategy.payport;

import com.example.strategy.Order;

/**
 * lingfan
 * 2019-07-08 16:24
 */
public class StrategyTest {

    public static void main(String[] args) {
        Order order = new Order("1","2019070811",500d);

        System.out.println(order.pay(PayStrategy.union));
    }
}
