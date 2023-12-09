package com.ams.studentattendance.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ams.studentattendance.R;
//import com.ams.studentattendance.bean.CheckUserBean;
import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPassword extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    Button btnreset;
    EditText txtconfpassword;
    TextInputLayout emailIL;
    TextInputLayout passwordIL;
    TextInputLayout confpassIL;

    InputValidatorHelper inputValidatorHelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
		
		getSupportActionBar().setTitle("Change Paassword");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnreset = (Button) findViewById(R.id.btnReset);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtconfpassword=(EditText) findViewById(R.id.txtconfPassword);
        emailIL = (TextInputLayout) findViewById(R.id.email_til);
        passwordIL = (TextInputLayout) findViewById(R.id.password_til);
        confpassIL=(TextInputLayout)findViewById(R.id.confpassword_til);
		
        inputValidatorHelper = new InputValidatorHelper();
        setValidations();
        btnreset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(!isAllValidated()) {
                    return;
                }
				
                String username = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String confirmpass=txtconfpassword.getText().toString();
				
                DatabaseHelper dbHelper = new DatabaseHelper(ResetPassword.this);
                boolean isValidUser = dbHelper.checkUser(username);
                if(isValidUser == true) {
						if (password.equals(confirmpass)) {
							confpassIL.setErrorEnabled(false);
                           int resetpass = dbHelper.resetpassword(username, password);
                           if (resetpass == 1) {
                               txtEmail.setText("");
                               txtPassword.setText("");
                               txtconfpassword.setText("");
							   
                               Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(ResetPassword.this, LoginScreen.class);
							   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                           }
                       } else {
							Toast.makeText(ResetPassword.this, "Password and Confirm password should be matched.", Toast.LENGTH_SHORT).show();
							confpassIL.setErrorEnabled(true);
							confpassIL.setError("Password and Confirm password should be matched.");
							confpassIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                       }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
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
        }  else {
            if (!inputValidatorHelper.isValidPassword(txtPassword.getText().toString().trim())) {
                res = false;
                passwordIL.setErrorEnabled(true);
                passwordIL.setError("The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?).");
                passwordIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            } else {
                passwordIL.setErrorEnabled(false);
            }
        }


        if(inputValidatorHelper.isNullOrEmpty(txtconfpassword.getText().toString())) {
            res = false;
            confpassIL.setErrorEnabled(true);
            confpassIL.setError("* Required");
            confpassIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if (!inputValidatorHelper.isValidPassword(txtconfpassword.getText().toString().trim())) {
                res = false;
                confpassIL.setErrorEnabled(true);
                passwordIL.setError("The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?).");
                confpassIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            } else {
                confpassIL.setErrorEnabled(false);
            }
        }

        return res;
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
                        if(!inputValidatorHelper.isValidPassword(txtPassword.getText().toString())) {
                            passwordIL.setErrorEnabled(true);
                            passwordIL.setError("The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?).");
                            passwordIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        passwordIL.setErrorEnabled(false);
                    }
                }
            }
        });
		
        txtconfpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtconfpassword.getText().toString())) {
                        confpassIL.setErrorEnabled(true);
                        confpassIL.setError("* Required");
                        confpassIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isValidPassword(txtconfpassword.getText().toString())) {
                            confpassIL.setErrorEnabled(true);
                            confpassIL.setError("The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?).");
                            confpassIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        confpassIL.setErrorEnabled(false);
                    }
                }
            }
        });
    }
	
	@Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
