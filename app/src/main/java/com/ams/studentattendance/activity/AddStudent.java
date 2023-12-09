package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ams.studentattendance.R;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewStudentBean;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AddStudent extends AppCompatActivity {
    EditText txtEnrollmentNumber;
    EditText txtAdmissionYear;
    EditText txtRollNumber;
    AutoCompleteTextView txtBranch;
    AutoCompleteTextView txtYear;
    AutoCompleteTextView txtSemester;
    AutoCompleteTextView txtDivision;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPhoneNumber;
    AutoCompleteTextView txtGender;
    EditText txtAddress;
    Button btnAddStudent;
    String gender, branch, year, semester, division;
    InputValidatorHelper inputValidatorHelper;
    TextInputLayout enrollmentNumberIL, admissionYearIL, rollNumberIL, branchIL, yearIL, semesterIl, divisionIL, firstNameIL, lastNameIL, emailIL, passwordIL, phoneNumberIL;
    ArrayAdapter<String> genderArrayAdapter, branchArrayAdapter, yearArrayAdapter, semesterArrayAdapter, divisionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setTitle("Add Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputValidatorHelper = new InputValidatorHelper();

        genderArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                DatabaseHelper.gender);

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
        DatabaseHelper dbHelper = new DatabaseHelper(AddStudent.this);

        branchArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                dbHelper.getAllBranch());

        txtBranch = (AutoCompleteTextView) findViewById(R.id.txtBranch);
        txtBranch.setAdapter(branchArrayAdapter);
        txtBranch.setText(branchArrayAdapter.getItem(0).toString(), false);
        branch = branchArrayAdapter.getItem(0).toString();
        txtBranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                branch = parent.getItemAtPosition(position).toString();
            }
        });

        yearArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                dbHelper.getAllYear());

        txtYear = (AutoCompleteTextView) findViewById(R.id.txtYear);
        txtSemester = (AutoCompleteTextView) findViewById(R.id.txtSemester);

        txtYear.setAdapter(yearArrayAdapter);
        txtYear.setText(yearArrayAdapter.getItem(0).toString(), false);
        year = yearArrayAdapter.getItem(0).toString();
        txtYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
                semesterArrayAdapter = new ArrayAdapter<>(
                        AddStudent.this,
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        dbHelper.getAllSemeseterByYear(year));
                txtSemester.setAdapter(semesterArrayAdapter);
                txtSemester.setText(semesterArrayAdapter.getItem(0).toString(), false);
                semester = semesterArrayAdapter.getItem(0).toString();
            }
        });

        semesterArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                dbHelper.getAllSemeseterByYear(year));

        txtSemester.setAdapter(semesterArrayAdapter);
        txtSemester.setText(semesterArrayAdapter.getItem(0).toString(), false);
        semester = semesterArrayAdapter.getItem(0).toString();
        txtSemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
            }
        });

        divisionArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                DatabaseHelper.division);

        txtDivision = (AutoCompleteTextView) findViewById(R.id.txtDivision);
        txtDivision.setAdapter(divisionArrayAdapter);
        txtDivision.setText(divisionArrayAdapter.getItem(0).toString(), false);
        division = divisionArrayAdapter.getItem(0).toString();
        txtDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                division = parent.getItemAtPosition(position).toString();
            }
        });

        btnAddStudent = (Button) findViewById(R.id.btnAddStudent);
        txtEnrollmentNumber= (EditText) findViewById(R.id.txtEnrollmentNumber);
        txtAdmissionYear= (EditText) findViewById(R.id.txtAdmissionYear);
        txtRollNumber = (EditText) findViewById(R.id.txtRollNumber);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtAddress = (EditText) findViewById(R.id.txtAddress);

        enrollmentNumberIL  = (TextInputLayout) findViewById(R.id.enrollmentNumber);
        admissionYearIL = (TextInputLayout) findViewById(R.id.admissionYear);
        rollNumberIL = (TextInputLayout) findViewById(R.id.rollNumber);
        branchIL = (TextInputLayout) findViewById(R.id.branch);
        yearIL = (TextInputLayout) findViewById(R.id.year);
        semesterIl = (TextInputLayout) findViewById(R.id.semester);
        divisionIL = (TextInputLayout) findViewById(R.id.division);
        firstNameIL = (TextInputLayout) findViewById(R.id.firstName);
        lastNameIL = (TextInputLayout) findViewById(R.id.lastName);
        emailIL = (TextInputLayout) findViewById(R.id.email_til);
        passwordIL = (TextInputLayout) findViewById(R.id.password_til);
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
        txtEnrollmentNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtEnrollmentNumber.getText().toString().trim())) {
                        enrollmentNumberIL.setErrorEnabled(true);
                        enrollmentNumberIL.setError("* Required");
                        enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isNumeric(txtEnrollmentNumber.getText().toString().trim())) {
                            enrollmentNumberIL.setErrorEnabled(true);
                            enrollmentNumberIL.setError("Invalid Enrollment number");
                            enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        if(!(txtEnrollmentNumber.getText().toString().trim().length() == 12)) {
                            enrollmentNumberIL.setErrorEnabled(true);
                            enrollmentNumberIL.setError("Enrollment number must be of 12 characters.");
                            enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        enrollmentNumberIL.setErrorEnabled(false);
                    }
                }
            }
        });

        txtAdmissionYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtAdmissionYear.getText().toString().trim())) {
                        admissionYearIL.setErrorEnabled(true);
                        admissionYearIL.setError("* Required");
                        admissionYearIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isNumeric(txtAdmissionYear.getText().toString().trim()) || txtAdmissionYear.getText().toString().trim().length() != 4 || Integer.valueOf(txtAdmissionYear.getText().toString().trim()) < 1993 || Integer.valueOf(txtAdmissionYear.getText().toString().trim()) > Calendar.getInstance().get(Calendar.YEAR)) {
                            admissionYearIL.setErrorEnabled(true);
                            admissionYearIL.setError("Invalid Admission Year");
                            admissionYearIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        admissionYearIL.setErrorEnabled(false);
                    }
                }
            }
        });

        txtRollNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(inputValidatorHelper.isNullOrEmpty(txtRollNumber.getText().toString().trim())) {
                        rollNumberIL.setErrorEnabled(true);
                        rollNumberIL.setError("* Required");
                        rollNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                    } else {
                        if(!inputValidatorHelper.isNumeric(txtRollNumber.getText().toString().trim()) || txtRollNumber.getText().toString().trim().length() != 3 || Integer.valueOf(txtRollNumber.getText().toString().trim()) < 0 || Integer.valueOf(txtRollNumber.getText().toString().trim()) > 400) {
                            rollNumberIL.setErrorEnabled(true);
                            rollNumberIL.setError("Invalid Roll number");
                            rollNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                            return;
                        }

                        rollNumberIL.setErrorEnabled(false);
                    }
                }
            }
        });

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
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddStudent.this);
                ViewStudentBean bean = new ViewStudentBean();

                if(!isAllValidated()) {
                    return;
                }

                bean.setFirstName(txtFirstName.getText().toString().trim());
                bean.setLastName(txtLastName.getText().toString().trim());
                bean.setEmail(txtEmail.getText().toString().trim());
                bean.setPassword(txtPassword.getText().toString().trim());
                bean.setPhoneNumber(txtPhoneNumber.getText().toString().trim());
                bean.setAddress(txtAddress.getText().toString().trim());
                bean.setGender(gender);
                bean.setEnrollmentNumber(txtEnrollmentNumber.getText().toString().trim());
                bean.setRollNumber(txtRollNumber.getText().toString().trim());
                bean.setAdmissionYear(Integer.parseInt(txtAdmissionYear.getText().toString().trim()));
                bean.setYear(year);
                bean.setSemester(semester);
                bean.setDivision(division);
                bean.setBranchName(branch);

                ViewStudentBean insertedStudentBean = dbHelper.addStudent(bean);
                if(insertedStudentBean == null) {
                    Toast.makeText(AddStudent.this, "Some error has occured!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddStudent.this, "Student Added!!!", Toast.LENGTH_LONG).show();
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtPhoneNumber.setText("");
                    txtAddress.setText("");

                    txtGender.setText(genderArrayAdapter.getItem(0).toString(), false);
                    gender = genderArrayAdapter.getItem(0).toString();

                    txtEnrollmentNumber.setText("");
                    txtRollNumber.setText("");
                    txtAdmissionYear.setText("");

                    txtYear.setText(yearArrayAdapter.getItem(0).toString(), false);
                    year = yearArrayAdapter.getItem(0).toString();

                    semesterArrayAdapter = new ArrayAdapter<>(
                            AddStudent.this,
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                            dbHelper.getAllSemeseterByYear(year));
                    txtSemester.setAdapter(semesterArrayAdapter);
                    txtSemester.setText(semesterArrayAdapter.getItem(0).toString(), false);
                    semester = semesterArrayAdapter.getItem(0).toString();

                    txtDivision.setText(divisionArrayAdapter.getItem(0).toString(), false);
                    division = divisionArrayAdapter.getItem(0).toString();

                    txtBranch.setText(branchArrayAdapter.getItem(0).toString(), false);
                    branch = branchArrayAdapter.getItem(0).toString();

                    txtEnrollmentNumber.requestFocus();
                }
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private boolean isAllValidated() {
        DatabaseHelper dbHelper = new DatabaseHelper(AddStudent.this);

        boolean res = true;

        if(inputValidatorHelper.isNullOrEmpty(txtEnrollmentNumber.getText().toString().trim())) {
            enrollmentNumberIL.setErrorEnabled(true);
            enrollmentNumberIL.setError("* Required");
            enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if(!inputValidatorHelper.isNumeric(txtEnrollmentNumber.getText().toString().trim())) {
                enrollmentNumberIL.setErrorEnabled(true);
                enrollmentNumberIL.setError("Invalid Enrollment number");
                enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
            } else if(!(txtEnrollmentNumber.getText().toString().trim().length() == 12)) {
                enrollmentNumberIL.setErrorEnabled(true);
                enrollmentNumberIL.setError("Enrollment number must be of 12 characters.");
                enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
            } else if(!dbHelper.isUniqueEnrollmentNumber(txtEnrollmentNumber.getText().toString().trim())) {
                enrollmentNumberIL.setErrorEnabled(true);
                enrollmentNumberIL.setError("Enrollment number already exists");
                enrollmentNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
            } else {
                enrollmentNumberIL.setErrorEnabled(false);
            }
        }

        if(inputValidatorHelper.isNullOrEmpty(txtAdmissionYear.getText().toString().trim())) {
            admissionYearIL.setErrorEnabled(true);
            admissionYearIL.setError("* Required");
            admissionYearIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if(!inputValidatorHelper.isNumeric(txtAdmissionYear.getText().toString().trim()) || txtAdmissionYear.getText().toString().trim().length() != 4 || Integer.valueOf(txtAdmissionYear.getText().toString().trim()) < 1993 || Integer.valueOf(txtAdmissionYear.getText().toString().trim()) > Calendar.getInstance().get(Calendar.YEAR)) {
                admissionYearIL.setErrorEnabled(true);
                admissionYearIL.setError("Invalid Admission Year");
                admissionYearIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
            } else {
                admissionYearIL.setErrorEnabled(false);
            }
        }

        if(inputValidatorHelper.isNullOrEmpty(txtRollNumber.getText().toString().trim())) {
            rollNumberIL.setErrorEnabled(true);
            rollNumberIL.setError("* Required");
            rollNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
        } else {
            if(!inputValidatorHelper.isNumeric(txtRollNumber.getText().toString().trim()) || txtRollNumber.getText().toString().trim().length() != 3 || Integer.valueOf(txtRollNumber.getText().toString().trim()) < 0 || Integer.valueOf(txtRollNumber.getText().toString().trim()) > 400) {
                rollNumberIL.setErrorEnabled(true);
                rollNumberIL.setError("Invalid Roll number");
                rollNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
                res = false;
            } else {
                rollNumberIL.setErrorEnabled(false);
            }
        }

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

        if(!dbHelper.isUniqueRollNo(txtRollNumber.getText().toString().trim(), txtYear.getText().toString().trim(), txtSemester.getText().toString().trim())) {
            rollNumberIL.setErrorEnabled(true);
            rollNumberIL.setError("Roll number already exists for the selected year");
            rollNumberIL.setErrorTextColor(ColorStateList.valueOf(com.google.android.material.R.color.design_default_color_error));
            res = false;
        } else {
            rollNumberIL.setErrorEnabled(false);
        }

        return res;
    }
}