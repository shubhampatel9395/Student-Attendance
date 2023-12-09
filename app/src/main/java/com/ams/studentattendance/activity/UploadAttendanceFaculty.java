package com.ams.studentattendance.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ams.studentattendance.R;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.DateValidatorSunday;
import com.ams.studentattendance.database.FileUtil;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.ams.studentattendance.dto.MappingHelperBean;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UploadAttendanceFaculty extends AppCompatActivity {
    AutoCompleteTextView txtYear;
    AutoCompleteTextView txtSemester;
    AutoCompleteTextView txtDivision;
    AutoCompleteTextView txtSubject;
    ArrayAdapter<String> subjectArrayAdapter, divisionArrayAdapter;
    MappingHelperBean subject;
    int subjectId, facultyId;
    String year, semester, division;
    String date;
    int day, month, yearInt;
    Button btnDate, btnSubmit, btnUpload;
    TextView txtDate, txtPath;
    DatabaseHelper dbHelper;
    InputValidatorHelper inputValidatorHelper;
    MaterialDatePicker.Builder materialDateBuilder;
    String selectedFilePath;
    ViewFacultyBean loggedInFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_attendance);

        getSupportActionBar().setTitle("Upload Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtYear = (AutoCompleteTextView) findViewById(R.id.txtYear);
        txtSemester = (AutoCompleteTextView) findViewById(R.id.txtSemester);
        txtPath = (TextView) findViewById(R.id.txtPath);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        loggedInFaculty = ((ApplicationContext) UploadAttendanceFaculty.this.getApplicationContext()).getFacultyBean();
        facultyId = loggedInFaculty.getFacultyId();

        dbHelper = new DatabaseHelper(UploadAttendanceFaculty.this);
        inputValidatorHelper = new InputValidatorHelper();

        subject = dbHelper.getAllAssignedSubjectsSpinner(loggedInFaculty.getFacultyId());
        subjectArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                subject.getValues());
//
//        divisionArrayAdapter = new ArrayAdapter<>(
//                this,
//                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
//                DatabaseHelper.division);
//
//        txtDivision = (AutoCompleteTextView) findViewById(R.id.txtDivision);
//        txtDivision.setAdapter(divisionArrayAdapter);
//        txtDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                division = parent.getItemAtPosition(position).toString();
//            }
//        });
//
        txtSubject = (AutoCompleteTextView) findViewById(R.id.txtSubject);
        txtSubject.setAdapter(subjectArrayAdapter);
        txtSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectId = subject.getIds().get(position);
                List<String> res = dbHelper.getYearSemesterBySubId(subjectId);
                if(res.size() != 0) {
                    year = res.get(0);
                    semester = res.get(1);
                    txtYear.setText(year);
                    txtSemester.setText(semester);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndOpenFilePicker();
            }
        });

        setInitialValues();
        buildDatePicker();
        performInsert();
    }

    private static final int REQUEST_PERMISSION = 123;
    private static final int REQUEST_FILE = 456;

    private boolean isExcelFile(String filePath) {
        // Check if the file has an Excel extension
        String extension = getFileExtension(filePath);
        return extension != null && (extension.equals("xls") || extension.equals("xlsx"));
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < filePath.length() - 1) {
            return filePath.substring(lastDotIndex + 1);
        }
        return null;
    }

    private void checkPermissionAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION
            );
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // MIME type for Excel files
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker();
            } else {
                // Handle permission denial
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                Log.d("uri path: ", selectedFileUri.getPath());
                selectedFilePath = FileUtil.getPath(this, selectedFileUri);
                Log.d("file path: ", selectedFilePath);

                if (selectedFilePath != null) {
                    // Display the selected file name
                    txtPath.setText("Selected Excel File:\n" + FileUtil.getFileName(this, selectedFileUri));
                } else {
                    Toast.makeText(this, "Error getting file path", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid file URI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Log.e("URI", String.valueOf(uri));

        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String filePath = cursor.getString(column_index);
                cursor.close();
                return filePath;
            }
        } else if (uri.getScheme() != null && uri.getScheme().equals("file")) {
            return uri.getPath();
        }

        return null;
    }

    private void setInitialValues() {
        txtPath.setText("");
//        txtDivision.setText(divisionArrayAdapter.getItem(0).toString(), false);
//        division = divisionArrayAdapter.getItem(0).toString();
        selectedFilePath = null;
        txtSubject.setText(subjectArrayAdapter.getItem(0).toString(), false);
        subjectId = subject.getIds().get(0);
        List<String> res = dbHelper.getYearSemesterBySubId(subjectId);
        if(res.size() != 0) {
            year = res.get(0);
            semester = res.get(1);
            txtYear.setText(year);
            txtSemester.setText(semester);
        }

    }

    public void buildDatePicker() {
        btnDate = (Button) findViewById(R.id.btnDate);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(new String());

        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Attendance Date");
        materialDateBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        List<CalendarConstraints.DateValidator> validators = new ArrayList<>();
        validators.add(DateValidatorPointBackward.now()); // disable future date, not today date
        validators.add(new DateValidatorSunday()); //disable Sunday
        // Disable Before 1 year past date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        validators.add(DateValidatorPointForward.from(calendar.getTimeInMillis()));

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        calendarConstraints.setValidator(CompositeDateValidator.allOf(validators));
        materialDateBuilder.setCalendarConstraints(calendarConstraints.build());

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        // String to Date
        String inputPattern = "dd MMM yyyy";
        String databasePattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat databaseFormat = new SimpleDateFormat(databasePattern);
        try {
            Date dateUtil = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
            date = databaseFormat.format(dateUtil);
            txtDate.setText("Date:\n" + inputFormat.format(dateUtil));
            Calendar cl = Calendar.getInstance();
            cl.setTime(dateUtil);

            Log.e("Date (DB): ", String.valueOf(date));
            yearInt = cl.get(Calendar.YEAR);
            day = cl.get(Calendar.DATE);
            month = cl.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        txtDate.setText("Date:\n" + materialDatePicker.getHeaderText());
                        String strDate = materialDatePicker.getHeaderText();

                        // Change date format from String Date
                        String out = inputValidatorHelper.parseDate(strDate);
                        Log.d("TAG", "formatDate:: " + out);

                        // String to Date
                        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                        String databasePattern = "yyyy-MM-dd";
                        SimpleDateFormat databaseFormat = new SimpleDateFormat(databasePattern);
                        try {
                            Date formattedDate = format.parse(strDate);
                            Date dateUtil = new java.sql.Date(formattedDate.getTime());
                            Calendar cl = Calendar.getInstance();
                            cl.setTime(dateUtil);
                            date = databaseFormat.format(dateUtil);

                            Log.e("Date (DB): ", String.valueOf(date));
                            yearInt = cl.get(Calendar.YEAR);
                            day = cl.get(Calendar.DATE);
                            month = cl.get(Calendar.MONTH) + 1;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void performInsert() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFilePath != null && isExcelFile(selectedFilePath)) {
                    List<String> res = dbHelper.uploadExcelFileToDatabase(UploadAttendanceFaculty.this, selectedFilePath, subjectId, facultyId, date, day, month, yearInt);
                    if(res.size() == 0) {
                        Toast.makeText(UploadAttendanceFaculty.this, "Some error has occured!!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UploadAttendanceFaculty.this, "Successful: " + res.get(0) + "\nUnsuccessful: " + res.get(1), Toast.LENGTH_LONG).show();
                        setInitialValues();
                        buildDatePicker();
                        btnDate.requestFocus();
                    }
                } else {
                    Toast.makeText(UploadAttendanceFaculty.this, "Invalid Excel file", Toast.LENGTH_LONG).show();
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