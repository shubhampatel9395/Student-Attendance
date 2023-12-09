package com.ams.studentattendance.dto;

import java.io.Serializable;

public class ViewFacultyBean implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String address;
    private String userType;

    private int facultyId;
    private String facultyType;
    private String academicQualification;

    public ViewFacultyBean(int userId, String firstName, String lastName, String email, String password, String phoneNumber, String gender, String address, String userType, int facultyId, String facultyType, String academicQualification) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.userType = userType;
        this.facultyId = facultyId;
        this.facultyType = facultyType;
        this.academicQualification = academicQualification;
    }

    @Override
    public String toString() {
        return "ViewFacultyBean{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", userType='" + userType + '\'' +
                ", facultyId=" + facultyId +
                ", facultyType='" + facultyType + '\'' +
                ", academicQualification='" + academicQualification + '\'' +
                '}';
    }

    public ViewFacultyBean() {
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
}
