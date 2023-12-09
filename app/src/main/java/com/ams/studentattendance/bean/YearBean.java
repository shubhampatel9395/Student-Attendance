package com.ams.studentattendance.bean;

public class YearBean {
    private int yearId;
    private String year;
    private int branchId;

    public YearBean() {
    }

    public YearBean(int yearId, String year, int branchId) {
        this.yearId = yearId;
        this.year = year;
        this.branchId = branchId;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "YearBean{" +
                "yearId=" + yearId +
                ", year='" + year + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
