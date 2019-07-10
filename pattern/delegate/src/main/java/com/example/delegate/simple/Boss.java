package com.example.delegate.simple;

/**
 * lingfan
 * 2019-07-08 15:08
 */
public class Boss {

    public void command(String command ,Employee leader){
        leader.doing(command);
    }
}
