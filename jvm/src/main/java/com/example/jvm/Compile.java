package com.example.jvm;

/**
 * lingfan
 * 2019-12-19 17:57
 */
public class Compile {

    public static int add(int a,int b){
        a= 11;
        b = 22;
        return a+b;
    }

    public static void main(String[] args) {
        System.out.println(add(2,3));
    }
}
