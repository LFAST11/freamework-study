package com.example.proxy.staticproxy;

/**
 * lingfan
 * 2019-06-14 16:13
 */
public class ZhenAiNet {

    public static void main(String[] args) {
        Father father = new Father(new Boy());

        father.findLove();
    }
}
