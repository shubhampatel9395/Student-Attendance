package com.ams.studentattendance.bean;

public class BranchBean {
    private int branchId;
    private String branchName;

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

    public BranchBean() {
    }

    public BranchBean(int branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "BranchBean{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
