package com.example.delegate.simple;

/**
 * lingfan
 * 2019-07-08 15:03
 */
public class EmployeeA  implements Employee{
    @Override
    public void doing(String command) {
        System.out.println("我是员工A，执行命令:"+command);
    }
}
