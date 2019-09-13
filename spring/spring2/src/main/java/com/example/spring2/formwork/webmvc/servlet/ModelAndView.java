package com.example.spring2.formwork.webmvc.servlet;

import lombok.Data;

import java.util.Map;

/**
 * lingfan
 * 2019-08-26 15:49
 */
@Data

public class ModelAndView {
    private String viewName;

    private Map<String,?> model;


    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
}
