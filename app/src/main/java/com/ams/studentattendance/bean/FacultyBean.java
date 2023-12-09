package com.ams.studentattendance.bean;

public class FacultyBean {
    private int facultyId;
    private String facultyType;
    private String academicQualification;
    private int userId;

    public FacultyBean(int facultyId, String facultyType, String academicQualification, int userId) {
        this.facultyId = facultyId;
        this.facultyType = facultyType;
        this.academicQualification = academicQualification;
        this.userId = userId;
    }

    public FacultyBean() {
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyType() {
        return facultyType;
    }

    public void setFacultyType(String facultyType) {
        this.facultyType = facultyType;
    }

    public String getAcademicQualification() {
        return academicQualification;
    }

    public void setAcademicQualification(String academicQualification) {
        this.academicQualification = academicQualification;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FacultyBean{" +
                "facultyId=" + facultyId +
                ", facultyType='" + facultyType + '\'' +
                ", academicQualification='" + academicQualification + '\'' +
                ", userId=" + userId +
                '}';
    }
}
