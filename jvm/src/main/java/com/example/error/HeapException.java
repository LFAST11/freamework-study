package com.example.error;

import java.util.ArrayList;
import java.util.List;

/**
 * lingfan
 * 2019-12-20 11:34
 */
public class HeapException {


    private static List<Student> list = new ArrayList<>();

    public static void add(){
        list.add(new Student());
    }

    public static void main(String[] args) {
        while (true){
            add();
        }
    }
}
