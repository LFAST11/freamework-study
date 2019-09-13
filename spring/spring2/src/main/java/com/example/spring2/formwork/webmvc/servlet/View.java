package com.example.spring2.formwork.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lingfan
 * 2019-08-26 15:56
 */
public class View {

    public final String default_conetent_type = "text/html;charset=utf-8";


    private File viewFile;

    public View(File viewFile) {
        this.viewFile = viewFile;
    }

    public void render(Map<String,?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sb = new StringBuffer();

        RandomAccessFile file = new RandomAccessFile(this.viewFile,"r");

        String line = null;

        while((line=file.readLine()) != null){
            line = new String(line.getBytes("ISO-8859-1"),"utf-8");
            Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                String findStr = matcher.group(0);
                findStr = findStr.replaceAll("￥\\{|\\}", "");
                Object value = model.get(findStr);
                if(value != null){
                    line = matcher.replaceFirst(value.toString());
                }
                //matcher = pattern.matcher(line);
            }
            sb.append(line);
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType(default_conetent_type);
        response.getWriter().write(sb.toString());

    }


}
