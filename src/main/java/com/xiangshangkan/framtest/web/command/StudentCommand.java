package com.xiangshangkan.framtest.web.command;

import java.io.Serializable;

public class StudentCommand implements Serializable {

    private static final long serialVersionUID = 4158261034722279830L;

    /** 学生姓名 */
    private String studentName;
    /** 学生编号 */
    private String studentNumber;
    /** 老师名称 */
    private String teacherName;
    /** 老师编号 */
    private String teacherNumber;
    /** 老师联系方式 */
    private String telphone;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
