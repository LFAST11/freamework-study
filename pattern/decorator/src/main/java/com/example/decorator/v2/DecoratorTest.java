package com.example.decorator.v2;

/**
 * lingfan
 * 2019-07-19 14:15
 */
public class DecoratorTest {

    public static void main(String[] args) {

        //一个煎饼
        BaseBatterCake baseBatterCake = new BaseBatterCake();


        //加一个鸡蛋
        EggDecorator eggDecorator = new EggDecorator(baseBatterCake);

        //加2个火腿

        HotDogDecorator hotDogDecorator = new HotDogDecorator(eggDecorator);
        HotDogDecorator hotDogDecorator2 = new HotDogDecorator(hotDogDecorator);

        System.out.println(hotDogDecorator2.getMsg()+"，总价格："+hotDogDecorator2.getValue());


    }
}
