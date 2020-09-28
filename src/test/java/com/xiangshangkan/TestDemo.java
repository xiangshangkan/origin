package com.xiangshangkan;

import org.junit.After;
import org.junit.Before;


public class TestDemo extends AbstractTest{



    @After
    public void tearDown() throws Exception {
       System.out.println("test after ...'");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("test before ...");
    }




}
