package com.xiangshangkan.framtest.service.auto.study.impl;

import com.xiangshangkan.framtest.auto.dao.study.TeacherEntityMapper;
import com.xiangshangkan.framtest.auto.entity.study.TeacherEntity;
import com.xiangshangkan.framtest.auto.entity.study.TeacherEntityExample;
import com.xiangshangkan.framtest.service.auto.study.TeacherAutoService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAutoServiceImpl implements TeacherAutoService {

    @Autowired
    private TeacherEntityMapper teacherEntityMapper;

    @Override
    public Integer insertTeacher(TeacherEntity record) {
        int num =this.insertTeacher(record);
        return record.getId();
    }

    @Override
    public List<TeacherEntity> selectTeacherSelective(TeacherEntity selection, Integer offset, Integer limit) {
        TeacherEntityExample example = new TeacherEntityExample();
        TeacherEntityExample.Criteria criteria = example.createCriteria();
        if (selection.getTeacherName() != null){ criteria.andTeacherNameEqualTo(selection.getTeacherName());}
        if (selection.getTeacherNumber() != null){ criteria.andTeacherNumberEqualTo(selection.getTeacherNumber());}
        if (selection.getTelphone() != null){ criteria.andTelphoneEqualTo(selection.getTelphone());}
       /* RowBounds rowBounds = new RowBounds(offset,limit);
        return this.teacherEntityMapper.selectByExampleWithRowbounds(example,rowBounds);*/
       return this.teacherEntityMapper.selectByExample(example);
    }
}
