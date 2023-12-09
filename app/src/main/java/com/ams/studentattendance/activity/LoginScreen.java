package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ams.studentattendance.R;
import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.google.android.material.textfield.TextInputLayout;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin;
    EditText txtEmail;
    EditText txtPassword;
	InputValidatorHelper inputValidatorHelper;
    TextInputLayout emailIL, passwordIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
		inputValidatorHelper = new InputValidatorHelper();
		emailIL = (TextInputLayout) findViewById(R.id.email_til);
        passwordIL = (TextInputLayout) findViewById(R.id.password_til);
		
		setValidations();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
					if(!isAllValidated()) {
						return;
					}
					
                    String username = txtEmail.getText().toString();
                    String password = txtPassword.getText().toString();

                    DatabaseHelper dbHelper = new DatabaseHelper(LoginScreen.this);
                    UserDetailsBean userDetailsBean = dbHelper.validateUser(username, password);
					
                    if(userDetailsBean != null) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            if(userDetailsBean.getUserType().equalsIgnoreCase("Admin")) {
                                Intent intent =new Intent(LoginScreen.this, AdminDashboard.class);
                                startActivity(intent);
                                ((ApplicationContext) LoginScreen.this.getApplicationContext()).setAdminBean(userDetailsBean);
                            } else if (userDetailsBean.getUserType().equalsIgnoreCase("Faculty")) {
                                Intent intent =new Intent(LoginScreen.this, FacultyDashboard.class);
                                startActivity(intent);
                                ((ApplicationContext) LoginScreen.this.getApplicationContext()).setFacultyBean(dbHelper.initializeFacultyDetails(userDetailsBean.getUserId()));
                            }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                    }
                }
        });
    }
	
	private void setValidations() {
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtEmail.getText().toString().trim())) {
                        emailIL.setErrorEnabled(true);
                        emailIL.setError("* Required");
                        emailIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isValidEmail(txtEmail.getText().toString().trim())) {
                            emailIL.setErrorEnabled(true);
                            emailIL.setError("Invalid Email Address");
                            emailIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        emailIL.setErrorEnabled(false);
                    }
                }
            }
        });

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtPassword.getText().toString())) {
                        passwordIL.setErrorEnabled(true);
                        passwordIL.setError("* Required");
                        passwordIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        passwordIL.setErrorEnabled(false);
                    }
                }
            }
        });
    }
	
	@SuppressLint("ResourceAsColor")
    private boolean isAllValidated() {
        boolean res = true;
        
        if(inputValidatorHelper.isNullOrEmpty(txtEmail.getText().toString().trim())) {
            res = false;
            emailIL.setErrorEnabled(true);
            emailIL.setError("* Required");
            emailIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if(!inputValidatorHelper.isValidEmail(txtEmail.getText().toString().trim())) {
                res = false;
                emailIL.setErrorEnabled(true);
                emailIL.setError("Invalid Email Address");
                emailIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            } else {
                emailIL.setErrorEnabled(false);
            }
        }

        if(inputValidatorHelper.isNullOrEmpty(txtPassword.getText().toString())) {
            res = false;
            passwordIL.setErrorEnabled(true);
            passwordIL.setError("* Required");
            passwordIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
			passwordIL.setErrorEnabled(false);
        }

        return res;
    }
}