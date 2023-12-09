package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ams.studentattendance.R;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.google.android.material.textfield.TextInputLayout;

public class AddFaculty extends AppCompatActivity {
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtEmail;
    EditText txtPassword;
    AutoCompleteTextView txtFacultyType;
    EditText txtAcademicQualification;
    EditText txtPhoneNumber;
    AutoCompleteTextView txtGender;
    EditText txtAddress;
    Button btnAddFaculty;
    String gender;
    String facultyType;
    InputValidatorHelper inputValidatorHelper;
    TextInputLayout firstNameIL, lastNameIL, emailIL, passwordIL, academicQualificatioIL, phoneNumberIL;
    ArrayAdapter<String> genderArrayAdapter;
    ArrayAdapter<String> facultyTypeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        getSupportActionBar().setTitle("Add Faculty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputValidatorHelper = new InputValidatorHelper();
        genderArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                DatabaseHelper.gender);

        facultyTypeArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                DatabaseHelper.facultyType);

        txtGender = (AutoCompleteTextView) findViewById(R.id.txtGender);
        txtGender.setAdapter(genderArrayAdapter);
        txtGender.setText(genderArrayAdapter.getItem(0).toString(), false);
        gender = genderArrayAdapter.getItem(0).toString();
        txtGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
        });

        txtFacultyType = (AutoCompleteTextView) findViewById(R.id.txtFacultyType);
        txtFacultyType.setAdapter(facultyTypeArrayAdapter);
        txtFacultyType.setText(facultyTypeArrayAdapter.getItem(0).toString(), false);
        facultyType = facultyTypeArrayAdapter.getItem(0).toString();
        txtFacultyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                facultyType = parent.getItemAtPosition(position).toString();
            }
        });

        btnAddFaculty = (Button) findViewById(R.id.btnAddFaculty);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtAcademicQualification = (EditText) findViewById(R.id.txtAcademicQualification);
        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtAddress = (EditText) findViewById(R.id.txtAddress);

        firstNameIL = (TextInputLayout) findViewById(R.id.firstName);
        lastNameIL = (TextInputLayout) findViewById(R.id.lastName);
        emailIL = (TextInputLayout) findViewById(R.id.email_til);
        passwordIL = (TextInputLayout) findViewById(R.id.password_til);
        academicQualificatioIL = (TextInputLayout) findViewById(R.id.academicQualification);
        phoneNumberIL = (TextInputLayout) findViewById(R.id.phoneNumber);

        setValidations();
        performInsert();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setValidations() {
        DatabaseHelper dbHelper = new DatabaseHelper(AddFaculty.this);

        txtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtFirstName.getText().toString().trim())) {
                        firstNameIL.setErrorEnabled(true);
                        firstNameIL.setError("* Required");
                        firstNameIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        firstNameIL.setErrorEnabled(false);
                    }
                }
            }
        });

        txtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtLastName.getText().toString().trim())) {
                        lastNameIL.setErrorEnabled(true);
                        lastNameIL.setError("* Required");
                        lastNameIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        lastNameIL.setErrorEnabled(false);
                    }
                }
            }
        });

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

        txtAcademicQualification.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtAcademicQualification.getText().toString().trim())) {
                        academicQualificatioIL.setErrorEnabled(true);
                        academicQualificatioIL.setError("* Required");
                        academicQualificatioIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        academicQualificatioIL.setErrorEnabled(false);
                    }
                }
            }
        });

        txtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtPhoneNumber.getText().toString().trim())) {
                        phoneNumberIL.setErrorEnabled(true);
                        phoneNumberIL.setError("* Required");
                        phoneNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isValidPhoneNumber(txtPhoneNumber.getText().toString().trim())) {
                            phoneNumberIL.setErrorEnabled(true);
                            phoneNumberIL.setError("Invalid Phone Number");
                            phoneNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        phoneNumberIL.setErrorEnabled(false);
                    }
                }
            }
        });
    }

    private void performInsert() {
        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddFaculty.this);
                ViewFacultyBean bean = new ViewFacultyBean();

                if(!isAllValidated()) {
                    return;
                }

                bean.setFirstName(txtFirstName.getText().toString().trim());
                bean.setLastName(txtLastName.getText().toString().trim());
                bean.setEmail(txtEmail.getText().toString().trim());
                bean.setPassword(txtPassword.getText().toString().trim());
                bean.setAcademicQualification(txtAcademicQualification.getText().toString().trim());
                bean.setPhoneNumber(txtPhoneNumber.getText().toString().trim());
                bean.setAddress(txtAddress.getText().toString().trim());
                bean.setGender(gender);
                bean.setFacultyType(facultyType);

                ViewFacultyBean insertedFacultyBean = dbHelper.addFaculty(bean);
                if(insertedFacultyBean == null) {
                    Toast.makeText(AddFaculty.this, "Some error has occured!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddFaculty.this, "Faculty Added!!!", Toast.LENGTH_LONG).show();
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtAcademicQualification.setText("");
                    txtPhoneNumber.setText("");
                    txtAddress.setText("");
                    txtGender.setText(genderArrayAdapter.getItem(0).toString(), false);
                    txtFacultyType.setText(facultyTypeArrayAdapter.getItem(0).toString(), false);
                    gender = genderArrayAdapter.getItem(0).toString();
                    facultyType = facultyTypeArrayAdapter.getItem(0).toString();
                    txtFirstName.requestFocus();
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private boolean isAllValidated() {
        DatabaseHelper dbHelper = new DatabaseHelper(AddFaculty.this);

        boolean res = true;
        if(inputValidatorHelper.isNullOrEmpty(txtFirstName.getText().toString().trim())) {
            res = false;
            firstNameIL.setErrorEnabled(true);
            firstNameIL.setError("* Required");
            firstNameIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            firstNameIL.setErrorEnabled(false);
        }

        if(inputValidatorHelper.isNullOrEmpty(txtLastName.getText().toString().trim())) {
            res = false;
            lastNameIL.setErrorEnabled(true);
            lastNameIL.setError("* Required");
            lastNameIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            lastNameIL.setErrorEnabled(false);
        }

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
            } else if(!dbHelper.isUniqueEmail(txtEmail.getText().toString().trim())) {
                emailIL.setErrorEnabled(true);
                emailIL.setError("Email address already exists");
                emailIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
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
            if(!inputValidatorHelper.isValidPassword(txtPassword.getText().toString())) {
                res = false;
                passwordIL.setErrorEnabled(true);
                passwordIL.setError("The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?).");
                passwordIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            }  else {
                passwordIL.setErrorEnabled(false);
            }
        }

        if(inputValidatorHelper.isNullOrEmpty(txtAcademicQualification.getText().toString().trim())) {
            res = false;
            academicQualificatioIL.setErrorEnabled(true);
            academicQualificatioIL.setError("* Required");
            academicQualificatioIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            academicQualificatioIL.setErrorEnabled(false);
        }

        if(inputValidatorHelper.isNullOrEmpty(txtPhoneNumber.getText().toString().trim())) {
            res = false;
            phoneNumberIL.setErrorEnabled(true);
            phoneNumberIL.setError("* Required");
            phoneNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if(!inputValidatorHelper.isValidPhoneNumber(txtPhoneNumber.getText().toString().trim())) {
                res = false;
                phoneNumberIL.setErrorEnabled(true);
                phoneNumberIL.setError("Invalid Phone Number");
                phoneNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            } else {
                phoneNumberIL.setErrorEnabled(false);
            }
        }

        return res;
    }
}