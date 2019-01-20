package com.xiangshangkan.framtest.service.auto.study;

import com.xiangshangkan.framtest.auto.entity.study.StudentEntity;
import com.xiangshangkan.framtest.auto.entity.study.TeacherEntity;

import java.util.List;

public interface TeacherAutoService {

    /**
     * 插入一条老师信息
     * @param record
     * @return
     */
    Integer insertTeacher(TeacherEntity record);

    /**
     * 条件查询
     * @param selection
     * @param offset 从第offset往后查limit条
     * @param limit
     * @return
     */
    List<TeacherEntity> selectTeacherSelective(TeacherEntity selection, Integer offset, Integer limit);
}
