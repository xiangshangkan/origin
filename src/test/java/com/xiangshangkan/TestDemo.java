package com.xiangshangkan;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestDemo {



    @After
    public void tearDown() throws Exception {
       System.out.println("test after ...'");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("test before ...");
    }




}
