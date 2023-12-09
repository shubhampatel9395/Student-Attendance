package com.ams.studentattendance.bean;

import java.sql.Date;

public class StudentAttendanceBean {
    private int attendanceId;
    private String enrollmentNumber;
    private String rollNumber;
    private String division;
    private int facultyId;
    private int subjectId;
    private Date date;
    private int day;
    private int month;
    private int year;
    private String academicYear;
    private String attendanceStatus;

    public StudentAttendanceBean() {
    }

    public StudentAttendanceBean(int attendanceId, String enrollmentNumber, String rollNumber, String division, int facultyId, int subjectId, Date date, int day, int month, int year, String academicYear, String attendanceStatus) {
        this.attendanceId = attendanceId;
        this.enrollmentNumber = enrollmentNumber;
        this.rollNumber = rollNumber;
        this.division = division;
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.academicYear = academicYear;
        this.attendanceStatus = attendanceStatus;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "StudentAttendanceBean{" +
                "attendanceId=" + attendanceId +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                ", division='" + division + '\'' +
                ", facultyId=" + facultyId +
                ", subjectId=" + subjectId +
                ", date=" + date +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", academicYear='" + academicYear + '\'' +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                '}';
    }
}
