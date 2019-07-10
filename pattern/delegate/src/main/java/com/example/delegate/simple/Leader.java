package com.example.delegate.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-07-08 15:05
 */
public class Leader implements Employee {

    public Map<String,Employee> target = new HashMap<>();

    public Leader() {
        target.put("加密",new EmployeeA());
        target.put("登陆",new EmployeeA());
    }

    @Override
    public void doing(String command) {
        target.get(command).doing(command);
    }
}
