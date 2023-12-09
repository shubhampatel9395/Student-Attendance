package com.ams.studentattendance.context;

import android.app.Application;

import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.dto.ViewFacultyBean;

public class ApplicationContext extends Application {
    private ViewFacultyBean facultyBean;
    private UserDetailsBean adminBean;

    public ViewFacultyBean getFacultyBean() {
        return facultyBean;
    }

    public void setFacultyBean(ViewFacultyBean facultyBean) {
        this.facultyBean = facultyBean;
    }

    public UserDetailsBean getAdminBean() {
        return adminBean;
    }

    public void setAdminBean(UserDetailsBean adminBean) {
        this.adminBean = adminBean;
    }
}
