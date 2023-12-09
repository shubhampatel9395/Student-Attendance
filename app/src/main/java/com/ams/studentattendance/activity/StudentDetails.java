package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.dto.ViewStudentBean;

public class StudentDetails extends AppCompatActivity {
    TextView txtVwUserId;
    TextView txtVwEnrollmentNumber;
    TextView txtVwBranch;
    TextView txtVwRollNumber;
    TextView txtVwAdmissionYear;
    TextView txtVwFirstName;
    TextView txtVwLastName;
    TextView txtVwEmail;
    TextView txtVwPhoneNumber;
    TextView txtYearSemester;
    TextView txtVwDivision;
    TextView txtVwGender;
    TextView txtVwAddress;
    TextView txtVwTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        getSupportActionBar().setTitle("Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewStudentBean studentBean = (ViewStudentBean) getIntent().getSerializableExtra("studentBean");
        txtVwUserId = (TextView) findViewById(R.id.txtVwUserId);
        txtVwEnrollmentNumber = (TextView) findViewById(R.id.txtVwEnrollmentNumber);
        txtVwBranch = (TextView) findViewById(R.id.txtVwBranch);
        txtVwRollNumber = (TextView) findViewById(R.id.txtVwRollNumber);
        txtVwAdmissionYear = (TextView) findViewById(R.id.txtVwAdmissionYear);
        txtVwFirstName = (TextView) findViewById(R.id.txtVwFirstName);
        txtVwLastName = (TextView) findViewById(R.id.txtVwLastName);
        txtVwEmail = (TextView) findViewById(R.id.txtVwEmail);
        txtVwPhoneNumber = (TextView) findViewById(R.id.txtVwPhoneNumber);
        txtYearSemester = (TextView) findViewById(R.id.txtYearSemester);
        txtVwDivision  = (TextView) findViewById(R.id.txtVwDivision);
        txtVwGender = (TextView) findViewById(R.id.txtVwGender);
        txtVwAddress = (TextView) findViewById(R.id.txtVwAddress);
        txtVwTitle =  (TextView) findViewById(R.id.txtVwTitle);

        txtVwTitle.setText(studentBean.getFirstName() + " " + studentBean.getLastName() + "'s Details");
        txtVwUserId.setText(String.valueOf(studentBean.getUserId()));
        txtVwEnrollmentNumber.setText(studentBean.getEnrollmentNumber());
        txtVwBranch.setText(studentBean.getBranchName());
        txtVwRollNumber.setText(studentBean.getRollNumber());
        txtVwAdmissionYear.setText(String.valueOf(studentBean.getAdmissionYear()));
        txtVwFirstName.setText(studentBean.getFirstName());
        txtVwLastName.setText(studentBean.getLastName());
        txtVwEmail.setText(studentBean.getEmail());
        txtVwPhoneNumber.setText(studentBean.getPhoneNumber());
        txtYearSemester.setText(studentBean.getYear() + "- " + studentBean.getSemester());
        txtVwDivision.setText(studentBean.getDivision());
        txtVwGender.setText(studentBean.getGender());
        txtVwAddress.setText(studentBean.getAddress());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}