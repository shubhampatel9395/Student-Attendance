package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.dto.ViewFacultyBean;

public class ViewFacultyProfile extends AppCompatActivity {
    TextView txtVwFirstName;
    TextView txtVwLastName;
    TextView txtVwEmail;
    TextView txtVwPhoneNumber;
    TextView txtVwGender;
    TextView txtVwAddress;
    TextView txtVwAcademicQualification;
    TextView txtVwFacultyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewFacultyBean facultyBean = ((ApplicationContext) ViewFacultyProfile.this.getApplicationContext()).getFacultyBean();
        txtVwFirstName = (TextView) findViewById(R.id.txtVwFirstName);
        txtVwLastName = (TextView) findViewById(R.id.txtVwLastName);
        txtVwEmail = (TextView) findViewById(R.id.txtVwEmail);
        txtVwPhoneNumber = (TextView) findViewById(R.id.txtVwPhoneNumber);
        txtVwGender = (TextView) findViewById(R.id.txtVwGender);
        txtVwAddress = (TextView) findViewById(R.id.txtVwAddress);
        txtVwAcademicQualification = (TextView) findViewById(R.id.txtVwAcademicQualification);
        txtVwFacultyType = (TextView) findViewById(R.id.txtVwFacultyType);

        txtVwFirstName.setText(facultyBean.getFirstName());
        txtVwLastName.setText(facultyBean.getLastName());
        txtVwEmail.setText(facultyBean.getEmail());
        txtVwPhoneNumber.setText(facultyBean.getPhoneNumber());
        txtVwGender.setText(facultyBean.getGender());
        txtVwAddress.setText(facultyBean.getAddress());
        txtVwAcademicQualification.setText(facultyBean.getAcademicQualification());
        txtVwFacultyType.setText(facultyBean.getFacultyType());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}