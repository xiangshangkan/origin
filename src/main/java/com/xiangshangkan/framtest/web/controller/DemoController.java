package com.xiangshangkan.framtest.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: Administrator
 * @date: 2018/12/8 16:15
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping()
    public void  outputHelloWorld(){
        System.out.println("hello world");
    }
}
