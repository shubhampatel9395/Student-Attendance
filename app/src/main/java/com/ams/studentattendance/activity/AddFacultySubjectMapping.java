package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.ams.studentattendance.dto.MappingHelperBean;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddFacultySubjectMapping extends AppCompatActivity {
    AutoCompleteTextView txtFaculty;
    AutoCompleteTextView txtSubject;
    ArrayAdapter<String> facultyArrayAdapter;
    ArrayAdapter<String> subjectArrayAdapter;
    MappingHelperBean faculty;
    MappingHelperBean subject;
    int facultyId, subjectId;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty_subject_mapping);

        getSupportActionBar().setTitle("Assign Subject's Faculty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper dbHelper = new DatabaseHelper(AddFacultySubjectMapping.this);
        faculty = dbHelper.getAllFacultySpinner();
        subject = dbHelper.getAllSubject();

        facultyArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                faculty.getValues());

        subjectArrayAdapter = new ArrayAdapter<>(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                subject.getValues());

        txtFaculty = (AutoCompleteTextView) findViewById(R.id.txtFaculty);
        txtFaculty.setAdapter(facultyArrayAdapter);
        txtFaculty.setText(facultyArrayAdapter.getItem(0).toString(), false);
        facultyId = faculty.getIds().get(0);
        txtFaculty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                facultyId = faculty.getIds().get(position);
            }
        });

        txtSubject = (AutoCompleteTextView) findViewById(R.id.txtSubject);
        txtSubject.setAdapter(subjectArrayAdapter);
        txtSubject.setText(subjectArrayAdapter.getItem(0).toString(), false);
        subjectId = subject.getIds().get(0);
        txtSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectId = subject.getIds().get(position);
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAddFacultySubjectMapping);

        performInsert();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void performInsert() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddFacultySubjectMapping.this);
                boolean result = dbHelper.addFacultySubjectMapping(facultyId, subjectId);
                Log.e("FacultyId: ", String.valueOf(facultyId));
                Log.e("SubjectId: ", String.valueOf(subjectId));

                if(!result) {
                    Toast.makeText(AddFacultySubjectMapping.this, "Some error has occured!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddFacultySubjectMapping.this, "Faculty Assigned!!!", Toast.LENGTH_LONG).show();

                    txtFaculty.setText(facultyArrayAdapter.getItem(0).toString(), false);
                    facultyId = faculty.getIds().get(0);

                    txtSubject.setText(subjectArrayAdapter.getItem(0).toString(), false);
                    subjectId = subject.getIds().get(0);

                    txtFaculty.requestFocus();
                }
            }
        });
    }
}