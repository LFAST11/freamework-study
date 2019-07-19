package com.example.adapter;

/**
 * lingfan
 * 2019-07-18 14:56
 */
public class AdaptTest {

    public static void main(String[] args) {
        Device keyDevcie = new KeyboardDevice();

        TransferAdaper adaper = new TransferAdaper();
        adaper.inputData(keyDevcie,"我是一个键盘，我来输入数据");
    }
}
