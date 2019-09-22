package com.mybaties.demo;

import lombok.Data;

/**
 * lingfan
 * 2019-09-22 13:47
 */
@Data
public class User {

    private Integer id;

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
