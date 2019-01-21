package com.xiangshangkan.framtest.service;

import com.xiangshangkan.framtest.web.command.StudentCommand;

public interface DemoService {

    /**
     * 插入学生信息
     * @param command
     */
    void insertStudentMessage(StudentCommand command);
}
