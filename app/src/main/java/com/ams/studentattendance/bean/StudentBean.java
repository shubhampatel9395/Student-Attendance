package com.ams.studentattendance.bean;

public class StudentBean {
    private String enrollmentNumber;
    private String rollNumber;
    private int admissionYear;
    private String year;
    private String semester;
    private String division;
    private int branchId;
    private String branchName;
    private int userId;

    public StudentBean() {
    }

    public StudentBean(String enrollmentNumber, String rollNumber, int admissionYear, String year, String semester, String division, int branchId, String branchName, int userId) {
        this.enrollmentNumber = enrollmentNumber;
        this.rollNumber = rollNumber;
        this.admissionYear = admissionYear;
        this.year = year;
        this.semester = semester;
        this.division = division;
        this.branchId = branchId;
        this.branchName = branchName;
        this.userId = userId;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "enrollmentNumber='" + enrollmentNumber + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                ", admissionYear=" + admissionYear +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", division='" + division + '\'' +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
