package com.mybaties.v1;

import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-09-20 14:23
 */
public class XmlConfiguration {

    public static final String nameSpace = "com.mybaties.v1";

    public  static final Map<String,String> methodCache = new HashMap<>();

    static{
        methodCache.put("selectByPrimary","select * from score_info where class_name = '%s'") ;
    }
}
