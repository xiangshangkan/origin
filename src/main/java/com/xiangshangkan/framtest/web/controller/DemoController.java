package com.xiangshangkan.framtest.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 
* @author      zhouhui
* @param       
* @date        2018/12/10 9:49
*/
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping()
    public void  outputHelloWorld(){
        System.out.println("hello world");
    }
}
