package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewFacultySubjectMappingBean;

public class FacultySubjectMappingDetails extends AppCompatActivity {
    TextView txtVwFacultyName;
    TextView txtVwSubjectCode;
    TextView txtVwSubjectName;
    TextView txtVwSubjectCredits;
    TextView txtVwYearSemester;
    TextView txtVwEmail;
    TextView txtVwPhoneNumber;
    TextView txtVwGender;
    TextView txtVwAcademicQualification;
    TextView txtVwFacultyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_subject_mapping_details);
        getSupportActionBar().setTitle("Assigned Subject's Faculty Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewFacultySubjectMappingBean bean = (ViewFacultySubjectMappingBean) getIntent().getSerializableExtra("bean");
        txtVwFacultyName = (TextView) findViewById(R.id.txtVwFacultyName);
        txtVwSubjectCode = (TextView) findViewById(R.id.txtVwSubjectCode);
        txtVwSubjectName = (TextView) findViewById(R.id.txtVwSubjectName);
        txtVwSubjectCredits = (TextView) findViewById(R.id.txtVwSubjectCredits);
        txtVwYearSemester = (TextView) findViewById(R.id.txtVwYearSemester);
        txtVwEmail = (TextView) findViewById(R.id.txtVwEmail);
        txtVwPhoneNumber = (TextView) findViewById(R.id.txtVwPhoneNumber);
        txtVwGender = (TextView) findViewById(R.id.txtVwGender);
        txtVwAcademicQualification = (TextView) findViewById(R.id.txtVwAcademicQualification);
        txtVwFacultyType = (TextView) findViewById(R.id.txtVwFacultyType);

        DatabaseHelper dbHelper = new DatabaseHelper(FacultySubjectMappingDetails.this);
        txtVwFacultyName.setText(bean.getViewFacultyBean().getFirstName() + " " + bean.getViewFacultyBean().getLastName());
        txtVwSubjectCode.setText(bean.getSubjectBean().getSubjectCode());
        txtVwSubjectName.setText(bean.getSubjectBean().getSubjectId() + " " + bean.getSubjectBean().getSubjectName());
        txtVwSubjectCredits.setText(bean.getSubjectBean().getSubjectCredits());
        txtVwYearSemester.setText(dbHelper.getYearSemesterBySemId(bean.getSubjectBean().getSemesterId()));
        txtVwEmail.setText(bean.getViewFacultyBean().getEmail());
        txtVwPhoneNumber.setText(bean.getViewFacultyBean().getPhoneNumber());
        txtVwGender.setText(bean.getViewFacultyBean().getGender());
        txtVwAcademicQualification.setText(bean.getViewFacultyBean().getAcademicQualification());
        txtVwFacultyType.setText(bean.getViewFacultyBean().getFacultyType());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}