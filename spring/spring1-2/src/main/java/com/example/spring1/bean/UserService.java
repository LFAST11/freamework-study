package com.example.spring1.bean;

import com.example.spring1.annotation.DiyService;

/**
 * lingfan
 * 2019-07-25 14:08
 */
@DiyService(name = "userService")
public class UserService {

    public String getUserByName(String name){
        return "this is "+name;
    }
}
