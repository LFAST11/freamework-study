package com.example.spring1.bean;

import com.example.spring1.annotation.DiyAutowired;
import com.example.spring1.annotation.DiyController;
import com.example.spring1.annotation.DiyMapper;
import com.example.spring1.annotation.DiyRequestParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * lingfan
 * 2019-07-25 14:10
 */
@DiyController
@DiyMapper(name = "/user")
public class UserController {

    @DiyAutowired
    private UserService userService;

    @DiyMapper(name="method1")
    public  void getUserByName(HttpServletRequest req, HttpServletResponse resp,  @DiyRequestParams(name="name") String name){
        String userByName = userService.getUserByName(name);
        try {
            resp.getWriter().write(userByName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DiyMapper(name="method2")
    public void  add(@DiyRequestParams(name="a") int a, @DiyRequestParams(name="b") int b,HttpServletResponse resp){
        String result = (a+b)+"";
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
