package com.example.spring2.formwork.webmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * lingfan
 * 2019-08-26 14:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleMapper {

    private Object controller;

    private Method method;

    private Pattern pattern;

}
