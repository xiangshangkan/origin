package com.xiangshangkan;

import com.alibaba.fastjson.JSON;
import com.xiangshangkan.framtest.auto.entity.study.StudentEntity;
import com.xiangshangkan.framtest.auto.entity.study.TeacherEntity;
import com.xiangshangkan.framtest.service.DemoService;
import com.xiangshangkan.framtest.service.importSome.ImportNhrQualification;
import com.xiangshangkan.framtest.web.command.StudentCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestDemo {

    @Autowired
    private DemoService demoService;

    @Autowired
    private ImportNhrQualification importNhrQualification;


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

    @Test
    public void testJsonAndStringConvert() throws Exception {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1);
        studentEntity.setStudentName("哈哈哈");
        String temp = JSON.toJSONString(studentEntity);
        TeacherEntity teacherEntity = JSON.parseObject(temp,TeacherEntity.class);
        System.out.println(temp);
        System.out.println(teacherEntity);
    }

    @Test
    public void testIndertNhrqualification() throws Exception {
       this.importNhrQualification.indertNhrqualification();
    }
}
