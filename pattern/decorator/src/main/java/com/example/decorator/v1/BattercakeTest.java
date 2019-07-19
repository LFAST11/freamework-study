package com.example.decorator.v1;

/**
 * lingfan
 * 2019-07-19 12:00
 */
public class BattercakeTest {

    /**
     * 需要加鸡蛋的煎饼，要写一个鸡蛋煎饼实现类
     * 需要加火腿的煎饼，要写一个火腿煎饼实现类
     * 但是如果需要加多个鸡蛋和火腿呢，这个时候就不符合需求了
     * @param args
     */
    public static void main(String[] args) {
        Battercake battercake = new Battercake();
        System.out.println(battercake.getMsg()+"，价格:"+battercake.getPrice());

        BattercakeWithEgg battercakeWithEgg = new BattercakeWithEgg();
        System.out.println(battercakeWithEgg.getMsg()+"，价格:"+battercakeWithEgg.getPrice());

        BattercakeWithHotDog battercakeWithHotDog = new BattercakeWithHotDog();
        System.out.println(battercakeWithHotDog.getMsg()+"，价格:"+battercakeWithHotDog.getPrice());


    }
}
