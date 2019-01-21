package com.xiangshangkan;

import com.xiangshangkan.framtest.service.DemoService;
import com.xiangshangkan.framtest.web.command.StudentCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestDemo {

    @Autowired
    private DemoService demoService;


    @After
    public void tearDown() throws Exception {
       System.out.println("test after ...'");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("test before ...");
    }

    @Test
    public void testInsertStudentMessage() {
        StudentCommand command = new StudentCommand();
        command.setStudentName("汪芳莉");
        command.setStudentNumber("201407126132");
            command.setTeacherName("单清");
        command.setTeacherNumber("4444");
        command.setTelphone("13678657654");
        this.demoService.insertStudentMessage(command);
    }
}
