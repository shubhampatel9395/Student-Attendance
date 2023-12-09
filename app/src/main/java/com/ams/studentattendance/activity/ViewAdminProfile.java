package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.dto.ViewFacultyBean;

public class ViewAdminProfile extends AppCompatActivity {
    TextView txtVwFirstName;
    TextView txtVwLastName;
    TextView txtVwEmail;
    TextView txtVwPhoneNumber;
    TextView txtVwGender;
    TextView txtVwAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_profile);

        getSupportActionBar().setTitle("View Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserDetailsBean adminBean = ((ApplicationContext) ViewAdminProfile.this.getApplicationContext()).getAdminBean();
        txtVwFirstName = (TextView) findViewById(R.id.txtVwFirstName);
        txtVwLastName = (TextView) findViewById(R.id.txtVwLastName);
        txtVwEmail = (TextView) findViewById(R.id.txtVwEmail);
        txtVwPhoneNumber = (TextView) findViewById(R.id.txtVwPhoneNumber);
        txtVwGender = (TextView) findViewById(R.id.txtVwGender);
        txtVwAddress = (TextView) findViewById(R.id.txtVwAddress);

        txtVwFirstName.setText(adminBean.getFirstName());
        txtVwLastName.setText(adminBean.getLastName());
        txtVwEmail.setText(adminBean.getEmail());
        txtVwPhoneNumber.setText(adminBean.getPhoneNumber());
        txtVwGender.setText(adminBean.getGender());
        txtVwAddress.setText(adminBean.getAddress());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}