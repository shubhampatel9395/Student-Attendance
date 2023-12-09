package com.ams.studentattendance.interfaces;

import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewFacultySubjectMappingBean;
import com.ams.studentattendance.dto.ViewStudentBean;

public interface OnClickListener {
    default void onItemClicked(ViewFacultyBean facultyBean) {}
    default void onItemClicked(ViewStudentBean facultyBean) {}
    default void onItemClicked(ViewFacultySubjectMappingBean bean) {}
}
