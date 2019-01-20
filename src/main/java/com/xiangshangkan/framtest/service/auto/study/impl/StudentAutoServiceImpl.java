package com.xiangshangkan.framtest.service.auto.study.impl;

import com.xiangshangkan.framtest.auto.dao.study.StudentEntityMapper;
import com.xiangshangkan.framtest.auto.entity.study.StudentEntity;
import com.xiangshangkan.framtest.auto.entity.study.StudentEntityExample;
import com.xiangshangkan.framtest.service.auto.study.StudentAutoService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAutoServiceImpl implements StudentAutoService {

    @Autowired
    private StudentEntityMapper studentEntityMapper;


    @Override
    public Integer insertStudent(StudentEntity record){
       int num = this.studentEntityMapper.insert(record);
       return record.getId();
    }

    @Override
    public List<StudentEntity> selectStudentSelective(StudentEntity  selection, Integer offset, Integer limit ){
        StudentEntityExample example = new StudentEntityExample();
        StudentEntityExample.Criteria criteria = example.createCriteria();
        if (selection.getId() != null) { criteria.andIdEqualTo(selection.getId());}
        if (selection.getStudentName() != null ) { criteria.andStudentNameEqualTo(selection.getStudentName());}
        if (selection.getStudentNumber() != null) { criteria.andStudentNumberEqualTo(selection.getStudentNumber());}
        if (selection.getTeacherId() != null) { criteria.andTeacherIdEqualTo(selection.getTeacherId());}
        return  this.studentEntityMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,limit));
    }




}
