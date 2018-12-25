package com.xiangshangkan.framtest.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* 
* @author      zhouhui
* @param       
* @date        2018/12/10 9:49
*/
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/toDemo")
    public String toDemo(){
        return "/demo/demo";
    }
}
