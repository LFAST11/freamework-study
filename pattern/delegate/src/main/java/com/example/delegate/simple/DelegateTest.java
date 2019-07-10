package com.example.delegate.simple;

/**
 * lingfan
 * 2019-07-08 15:09
 */
public class DelegateTest {

    /**
     * Boss-客户请求，Leader-委派者，Employee-执行者
     * 委派者需要拥有执行者的引用
     * 代理过程注重的是过程加强，委派模式注重的是结果返回
     *
     * 委派模式的核心就是　分发，调度，派遣
     * 委派模式可以看成是静态代理和策略模式的一种特殊的组合
     */
    public static void main(String[] args) {
        new Boss().command("加密",new Leader());
    }
}
