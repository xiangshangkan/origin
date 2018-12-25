package com.xiangshangkan.framtest.consts;

import java.util.HashMap;
import java.util.Map;

public class DemoConstant {

    public static Map<String,Object> cacheMap = new HashMap<>();

    static {
        cacheMap.put("id","1");
        cacheMap.put("name","小明");
        cacheMap.put("number","201407126");
    }

}
