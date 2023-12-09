package com.ams.studentattendance.dto;

import com.ams.studentattendance.bean.StudentBean;

import java.io.Serializable;

public class MarkAttendanceStudentBean implements Serializable {
    private ViewStudentBean studentBean;
    private boolean isChecked;

    public MarkAttendanceStudentBean() {
    }

    public MarkAttendanceStudentBean(ViewStudentBean studentBean) {
        this.studentBean = studentBean;
        this.isChecked = false;
    }

    public MarkAttendanceStudentBean(ViewStudentBean studentBean, boolean isChecked) {
        this.studentBean = studentBean;
        this.isChecked = isChecked;
    }

    public ViewStudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(ViewStudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
