package com.example.test;

/**
 * lingfan
 * 2019-10-25 11:41
 */
public class TestDemo1 {

    int i=0;

    Integer j=0;

    public void test1(){
        i=2;
    }

    public void test2(int num){
        num++;
    }

    public void test3(Integer num){
        num++;
    }


    public void test4(TestDemo1 testDemo){
        testDemo.j++;
    }


    public static void main(String[] args) {
        TestDemo1 demo1 = new TestDemo1();
        System.out.println(demo1.i);

        demo1.test1();
        System.out.println(demo1.i);

        demo1.test2(demo1.i);
        System.out.println(demo1.i);


        demo1.test3(demo1.j);
        System.out.println(demo1.j);


        demo1.test4(demo1);
        System.out.println(demo1.j);

    }
}
