package com.xiangshangkan.framtest.web.controller.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xiangshangkan.framtest.consts.DemoConstant.cacheMap;

@Controller
@RequestMapping("/demoRestfull")
public class DemoRestfulController {
    @RequestMapping("/returnCacheMap")
    @ResponseBody
    public Map<String,Object> returnCacheMap(String name, String id ){
        cacheMap.put("id",cacheMap.get("id") +"+"+ id);
        cacheMap.put("name",cacheMap.get("name") +"+"+ name);
        cacheMap.put("date",new Date());
        System.out.println(cacheMap);
        return cacheMap;
    }

    @RequestMapping("/returnValue")
    @ResponseBody
    public Map<String,Object> returnValue(String timeStamp){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("timeStamp",timeStamp);
        return resultMap;
    }

}
