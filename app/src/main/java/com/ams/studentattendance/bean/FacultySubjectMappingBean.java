package com.ams.studentattendance.bean;

public class FacultySubjectMappingBean {
    private int facultySubjectMappingId;
    private int facultyId;
    private int subjectId;

    public FacultySubjectMappingBean() {
    }

    public FacultySubjectMappingBean(int facultySubjectMappingId, int facultyId, int subjectId) {
        this.facultySubjectMappingId = facultySubjectMappingId;
        this.facultyId = facultyId;
        this.subjectId = subjectId;
    }

    public int getFacultySubjectMappingId() {
        return facultySubjectMappingId;
    }

    public void setFacultySubjectMappingId(int facultySubjectMappingId) {
        this.facultySubjectMappingId = facultySubjectMappingId;
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

    @Override
    public String toString() {
        return "FacultySubjectMappingBean{" +
                "facultySubjectMappingId=" + facultySubjectMappingId +
                ", facultyId=" + facultyId +
                ", subjectId=" + subjectId +
                '}';
    }
}
