package com.ams.studentattendance.dto;

import java.io.Serializable;
import java.util.Objects;

public class ViewStudentBean implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String address;
    private String userType;

    private String enrollmentNumber;
    private String rollNumber;
    private int admissionYear;
    private String year;
    private String semester;
    private String division;
    private int branchId;
    private String branchName;

    public ViewStudentBean() {
    }

    public ViewStudentBean(int userId, String firstName, String lastName, String email, String password, String phoneNumber, String gender, String address, String userType, String enrollmentNumber, String rollNumber, int admissionYear, String year, String semester, String division, int branchId, String branchName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.userType = userType;
        this.enrollmentNumber = enrollmentNumber;
        this.rollNumber = rollNumber;
        this.admissionYear = admissionYear;
        this.year = year;
        this.semester = semester;
        this.division = division;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    @Override
    public String toString() {
        return "ViewStudentBean{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", userType='" + userType + '\'' +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                ", admissionYear=" + admissionYear +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", division='" + division + '\'' +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewStudentBean that = (ViewStudentBean) o;
        return enrollmentNumber.equals(that.enrollmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentNumber);
    }
}
