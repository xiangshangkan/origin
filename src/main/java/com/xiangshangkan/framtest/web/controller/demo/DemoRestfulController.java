package com.xiangshangkan.framtest.web.controller.demo;

import com.xiangshangkan.framtest.service.DemoService;
import com.xiangshangkan.framtest.web.command.StudentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/demoRestfull")
public class DemoRestfulController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/returnValue")
    @ResponseBody
    public Map<String,Object> returnValue(String timeStamp){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("timeStamp",timeStamp);
        return resultMap;
    }

    @RequestMapping("/saveStudentMessage")
    @ResponseBody
    public Object saveStudentMessage( @RequestBody StudentCommand command){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            this.demoService.insertStudentMessage(command);
            resultMap.put("success",true);
            resultMap.put("msg","保存信息成功！");
        } catch (IllegalArgumentException e) {
            resultMap.put("success",false);
            resultMap.put("msg",e.getMessage());
        } catch (Exception e) {
            resultMap.put("success",false);
            resultMap.put("msg","保存信息失败！");
        }
        return resultMap;
    }

}
