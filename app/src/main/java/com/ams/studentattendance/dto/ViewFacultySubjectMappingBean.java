package com.ams.studentattendance.dto;

import com.ams.studentattendance.bean.SubjectBean;

import java.io.Serializable;

public class ViewFacultySubjectMappingBean implements Serializable {
    ViewFacultyBean viewFacultyBean;
    SubjectBean subjectBean;

    public ViewFacultyBean getViewFacultyBean() {
        return viewFacultyBean;
    }

    public void setViewFacultyBean(ViewFacultyBean viewFacultyBean) {
        this.viewFacultyBean = viewFacultyBean;
    }

    public SubjectBean getSubjectBean() {
        return subjectBean;
    }

    public void setSubjectBean(SubjectBean subjectBean) {
        this.subjectBean = subjectBean;
    }

    @Override
    public String toString() {
        return "ViewFacultySubjectMappingBean{" +
                "viewFacultyBean=" + viewFacultyBean +
                ", subjectBean=" + subjectBean +
                '}';
    }

    public ViewFacultySubjectMappingBean() {
    }

    public ViewFacultySubjectMappingBean(ViewFacultyBean viewFacultyBean, SubjectBean subjectBean) {
        this.viewFacultyBean = viewFacultyBean;
        this.subjectBean = subjectBean;
    }
}
