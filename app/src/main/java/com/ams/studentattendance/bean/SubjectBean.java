package com.ams.studentattendance.bean;

import java.io.Serializable;

public class SubjectBean implements Serializable {
    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private String subjectCredits;
    private int semesterId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCredits() {
        return subjectCredits;
    }

    public void setSubjectCredits(String subjectCredits) {
        this.subjectCredits = subjectCredits;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public String toString() {
        return "SubjectBean{" +
                "subjectId=" + subjectId +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", subjectCredits='" + subjectCredits + '\'' +
                ", semesterId=" + semesterId +
                '}';
    }

    public SubjectBean() {
    }

    public SubjectBean(int subjectId, String subjectCode, String subjectName, String subjectCredits, int semesterId) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectCredits = subjectCredits;
        this.semesterId = semesterId;
    }
}
