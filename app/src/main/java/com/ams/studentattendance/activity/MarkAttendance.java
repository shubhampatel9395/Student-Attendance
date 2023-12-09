package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ams.studentattendance.R;
import com.ams.studentattendance.adapter.AttendanceAdapter;
import com.ams.studentattendance.adapter.StudentAdapter;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.DateValidatorSunday;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.ams.studentattendance.dto.MappingHelperBean;
import com.ams.studentattendance.dto.MarkAttendanceStudentBean;
import com.ams.studentattendance.dto.ViewStudentBean;
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

public class MarkAttendance extends AppCompatActivity {
    AutoCompleteTextView txtYear;
    AutoCompleteTextView txtSemester;
    AutoCompleteTextView txtDivision;
    AutoCompleteTextView txtSubject;
    ArrayAdapter<String> subjectArrayAdapter, divisionArrayAdapter;
    MappingHelperBean subject;
    int subjectId;
    String year, semester, division;
    String date;
    int day, month, yearInt;
    Button btnDate, btnSubmit;
    TextView txtDate;
    DatabaseHelper dbHelper;
    InputValidatorHelper inputValidatorHelper;
    CardView cardView;
    CheckBox chkSelectAll;
    private RecyclerView card_recyclerView;
    private AttendanceAdapter attendanceAdapter;
    MaterialDatePicker.Builder materialDateBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        getSupportActionBar().setTitle("Mark Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtYear = (AutoCompleteTextView) findViewById(R.id.txtYear);
        txtSemester = (AutoCompleteTextView) findViewById(R.id.txtSemester);
        chkSelectAll = (CheckBox) findViewById(R.id.chkSelectedAll);
        cardView = (CardView) findViewById(R.id.cardSelectAll);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        initViews();
        loadStudents();
        initSelectAll();
        performInsert();

        dbHelper = new DatabaseHelper(MarkAttendance.this);
        inputValidatorHelper = new InputValidatorHelper();

        subject = dbHelper.getAllSubject();
        subjectArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                subject.getValues());

        divisionArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                DatabaseHelper.division);

        txtDivision = (AutoCompleteTextView) findViewById(R.id.txtDivision);
        txtDivision.setAdapter(divisionArrayAdapter);
        txtDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                division = parent.getItemAtPosition(position).toString();
                changeStudentData();
            }
        });

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
                changeStudentData();
            }
        });

        setInitialValues();
        buildDatePicker();
    }

    private void performInsert() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MarkAttendanceStudentBean> students = attendanceAdapter.getStudentList();
                boolean res = dbHelper.insertAttendance(students, division, subjectId, -1, date, day, month, yearInt);
                if(!res) {
                    Toast.makeText(MarkAttendance.this, "Some error has occured!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MarkAttendance.this, "Attendance Marked Successfully!!!", Toast.LENGTH_LONG).show();
                    setInitialValues();
                    chkSelectAll.setChecked(false);
                    attendanceAdapter.unSelectAll();
                    buildDatePicker();
                    txtSubject.requestFocus();
                }
            }
        });
    }

    private void setInitialValues() {
        txtDivision.setText(divisionArrayAdapter.getItem(0).toString(), false);
        division = divisionArrayAdapter.getItem(0).toString();

        txtSubject.setText(subjectArrayAdapter.getItem(0).toString(), false);
        subjectId = subject.getIds().get(0);
        List<String> res = dbHelper.getYearSemesterBySubId(subjectId);
        if(res.size() != 0) {
            year = res.get(0);
            semester = res.get(1);
            txtYear.setText(year);
            txtSemester.setText(semester);
        }
        changeStudentData();
    }

    private void loadStudents() {
        attendanceAdapter = new AttendanceAdapter(this, new ArrayList<>());
        card_recyclerView.setAdapter(attendanceAdapter);
    }

    private void initViews() {
        card_recyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(layoutManager);
    }

    private void changeStudentData() {
        attendanceAdapter.setStudentList(dbHelper.getStudentByYearSemDiv(year, semester, division));
        if(attendanceAdapter.getStudentList().size() == 0) {
            cardView.setVisibility(View.INVISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
            chkSelectAll.setChecked(false);
            attendanceAdapter.unSelectAll();
        } else {
            cardView.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }

    private void initSelectAll() {
        chkSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkSelectAll.isChecked()) {
                    attendanceAdapter.selectAll();
                } else {
                    attendanceAdapter.unSelectAll();
                }
            }
        });
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}