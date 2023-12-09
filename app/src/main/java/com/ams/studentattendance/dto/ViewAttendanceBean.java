package com.ams.studentattendance.dto;

import com.ams.studentattendance.bean.StudentAttendanceBean;

import java.io.Serializable;

public class ViewAttendanceBean implements Serializable {
    private StudentAttendanceBean attendanceBean;
    private String studentName;
    private String facultyName;
    private String date;

    public ViewAttendanceBean() {
    }

    public ViewAttendanceBean(StudentAttendanceBean attendanceBean, String studentName, String facultyName, String date) {
        this.attendanceBean = attendanceBean;
        this.studentName = studentName;
        this.facultyName = facultyName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StudentAttendanceBean getAttendanceBean() {
        return attendanceBean;
    }

    public void setAttendanceBean(StudentAttendanceBean attendanceBean) {
        this.attendanceBean = attendanceBean;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "ViewAttendanceBean{" +
                "attendanceBean=" + attendanceBean +
                ", studentName='" + studentName + '\'' +
                ", facultyName='" + facultyName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
