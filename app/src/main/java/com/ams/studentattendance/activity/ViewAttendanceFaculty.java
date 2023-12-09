package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TableLayout;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.adapter.ViewAttendanceAdapter;
import com.ams.studentattendance.bean.StudentAttendanceBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.database.DateValidatorSunday;
import com.ams.studentattendance.database.InputValidatorHelper;
import com.ams.studentattendance.dto.MappingHelperBean;
import com.ams.studentattendance.dto.ViewAttendanceBean;
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

public class ViewAttendanceFaculty extends AppCompatActivity {
    AutoCompleteTextView txtYear;
    AutoCompleteTextView txtSemester;
    AutoCompleteTextView txtDivision;
    AutoCompleteTextView txtSubject;
    AutoCompleteTextView txtFaculty;
    ArrayAdapter<String> subjectArrayAdapter, facultyArrayAdapter, divisionArrayAdapter, yearArrayAdapter, semesterArrayAdapter;
    MappingHelperBean subject;
    MappingHelperBean faculty;
    int facultyId, subjectId;
    String year, semester, division;
    String date;
    int day, month, yearInt;
    Button btnDate, btnSubmit;
    TextView txtDate;
    DatabaseHelper dbHelper;
    InputValidatorHelper inputValidatorHelper;
    private RecyclerView card_recyclerView;
    private ViewAttendanceAdapter attendanceAdapter;
    MaterialDatePicker.Builder materialDateBuilder;
    List<String> lstDiv, lstFaculty;
    TableLayout table_heading_layout;
    ViewFacultyBean loggedInFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        getSupportActionBar().setTitle("View Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtSubject = (AutoCompleteTextView) findViewById(R.id.txtSubject);
        txtFaculty = (AutoCompleteTextView) findViewById(R.id.txtFaculty);
        txtYear = (AutoCompleteTextView) findViewById(R.id.txtYear);
        txtSemester = (AutoCompleteTextView) findViewById(R.id.txtSemester);
        txtDivision = (AutoCompleteTextView) findViewById(R.id.txtDivision);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        table_heading_layout = (TableLayout) findViewById(R.id.table_heading_layout);
        table_heading_layout.setVisibility(View.INVISIBLE);
        loggedInFaculty = ((ApplicationContext) ViewAttendanceFaculty.this.getApplicationContext()).getFacultyBean();

        dbHelper = new DatabaseHelper(ViewAttendanceFaculty.this);
        inputValidatorHelper = new InputValidatorHelper();

        initViews();
        setInitialValues();
        showData();
        buildDatePicker();
    }

    private void showData() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentAttendanceBean studentAttendanceBean = new StudentAttendanceBean();
                studentAttendanceBean.setDivision(division);
                studentAttendanceBean.setFacultyId(facultyId);
                studentAttendanceBean.setSubjectId(subjectId);
                studentAttendanceBean.setAcademicYear(year);

                List<ViewAttendanceBean> list = dbHelper.getAttendance(studentAttendanceBean, date);
                attendanceAdapter.setAttendanceList(list);

                if(attendanceAdapter.getAttendanceList().size() == 0) {
                    table_heading_layout.setVisibility(View.INVISIBLE);
                } else {
                    table_heading_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initViews() {
        card_recyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(layoutManager);
        attendanceAdapter = new ViewAttendanceAdapter(this, new ArrayList<>());
        card_recyclerView.setAdapter(attendanceAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setInitialValues() {
        lstDiv = new ArrayList<>();
        lstDiv.add("Select");
        lstDiv.addAll(DatabaseHelper.division);

        subject = dbHelper.getAllAssignedSubjectsSpinner(loggedInFaculty.getFacultyId());
        subjectArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                subject.getValues());

        divisionArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                lstDiv);

        yearArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                dbHelper.getAllYear());

        txtDivision.setAdapter(divisionArrayAdapter);
        txtDivision.setText(divisionArrayAdapter.getItem(0).toString(), false);
        division = null;
        txtDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    division = null;
                } else {
                    division = parent.getItemAtPosition(position).toString();
                }
            }
        });

        txtSubject.setAdapter(subjectArrayAdapter);
        txtSubject.setText(subjectArrayAdapter.getItem(0).toString(), false);
        subjectId = subject.getIds().get(0);
        List<String> res = dbHelper.getYearSemesterBySubId(subjectId);
        if(res.size() != 0) {
            year = res.get(0);
            semester = res.get(1);
            txtYear.setText(year);
            txtSemester.setText(semester);
        }

        facultyId = loggedInFaculty.getFacultyId();
        txtFaculty.setText(loggedInFaculty.getFirstName() + " " + loggedInFaculty.getLastName());
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
}