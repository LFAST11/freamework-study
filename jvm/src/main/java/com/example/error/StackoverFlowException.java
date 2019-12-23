package com.example.error;

/**
 * lingfan
 * 2019-12-20 11:05
 */
public class StackoverFlowException {

    private static int count = 0;

    public static void method1(){
        count++;
        System.out.println(count);
        method1();
    }

    public static void main(String[] args) {
        method1();
    }
}
