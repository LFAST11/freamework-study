package com.example.spring2.formwork.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * lingfan
 * 2019-08-26 14:31
 */
public class ViewResolver {

    private final String default_template_suffx = ".html";

    private File templateRootDir;

    public ViewResolver(String templatPath) {
        String filePath = this.getClass().getClassLoader().getResource(templatPath).getFile();
        templateRootDir = new File(filePath);
    }

    public View resolveViewName(String viewName, Locale locale){
        if(viewName == null) return null;

        viewName = viewName.endsWith(default_template_suffx) ? viewName : viewName + default_template_suffx;
        File file = new File((templateRootDir.getPath() + "/" + viewName).replace("/+", "/"));
        return new View(file);
    }

}
