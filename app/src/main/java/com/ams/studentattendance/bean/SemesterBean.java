package com.ams.studentattendance.bean;

public class SemesterBean {
    private int semesterId;
    private String semester;
    private int yearId;

    public SemesterBean(int semesterId, String semester, int yearId) {
        this.semesterId = semesterId;
        this.semester = semester;
        this.yearId = yearId;
    }

    public SemesterBean() {
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    @Override
    public String toString() {
        return "SemesterBean{" +
                "semesterId=" + semesterId +
                ", semester='" + semester + '\'' +
                ", yearId=" + yearId +
                '}';
    }
}
