package com.xiangshangkan.framtest.service.impl;

import com.xiangshangkan.framtest.auto.entity.study.StudentEntity;
import com.xiangshangkan.framtest.auto.entity.study.TeacherEntity;
import com.xiangshangkan.framtest.service.DemoService;
import com.xiangshangkan.framtest.service.auto.study.StudentAutoService;
import com.xiangshangkan.framtest.service.auto.study.TeacherAutoService;
import com.xiangshangkan.framtest.web.command.StudentCommand;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private StudentAutoService studentAutoService;
    @Autowired
    private TeacherAutoService teacherAutoService;


    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    @Override
    public void insertStudentMessage(StudentCommand command){
        Assert.notNull(command,"获取参数失败！");
        Assert.hasLength(command.getStudentName(),"学生名称不能为空！");
        Assert.hasLength(command.getStudentNumber(),"学生编号不能为空！");
        Assert.hasLength(command.getTeacherName(),"老师姓名不能为空！");
        Assert.hasLength(command.getTeacherNumber(),"老师编号不能为空！");
        Assert.hasLength(command.getTelphone(),"老师联系方式不能为空！");

        TeacherEntity teacherSelect = new TeacherEntity();
        teacherSelect.setTeacherNumber(command.getTeacherNumber());
        List<TeacherEntity> teacherEntityList = this.teacherAutoService.selectTeacherSelective(teacherSelect,0,1);
        Integer teacherId = null;
        if (CollectionUtils.isEmpty(teacherEntityList)){
            BeanUtils.copyProperties(command,teacherSelect);
            teacherId = this.teacherAutoService.insertTeacher(teacherSelect);
            if (teacherId == null) {
                throw  new RuntimeException();
            }
        } else {
            teacherId = teacherEntityList.get(0).getId();
        }
        StudentEntity studentRecord = new StudentEntity();
        BeanUtils.copyProperties(command,studentRecord);
        studentRecord.setTeacherId(teacherId);
        Integer studentId = this.studentAutoService.insertStudent(studentRecord);
        if (studentId == null) {
            throw  new RuntimeException();
        }
    }
}
