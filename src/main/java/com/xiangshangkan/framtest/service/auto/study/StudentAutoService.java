package com.xiangshangkan.framtest.service.auto.study;

import com.xiangshangkan.framtest.auto.entity.study.StudentEntity;

import java.util.List;

public interface StudentAutoService {

    /**
     * 插入一条Student数据
     * @param record
     * @return
     */
    Integer insertStudent(StudentEntity record);

    /**
     * 条件查询
     * @param selection 属性条件
     * @param offset 从第offset条开始往后limit条结束
     * @param limit
     * @return
     */
    List<StudentEntity> selectStudentSelective(StudentEntity  selection, Integer offset, Integer limit );
}
