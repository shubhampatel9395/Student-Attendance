package com.ams.studentattendance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ams.studentattendance.R;
import com.ams.studentattendance.adapter.FacultyAdapter;
import com.ams.studentattendance.adapter.SubjectAdapter;
import com.ams.studentattendance.bean.SubjectBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignedSubjects extends AppCompatActivity {
    private RecyclerView card_recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<SubjectBean> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assigned_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Assigned Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        loadSubjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        search.setVisible(false);
        return true;
    }

    private void loadSubjects() {
        DatabaseHelper dbHelper = new DatabaseHelper(ViewAssignedSubjects.this);
        subjectList = new ArrayList<>(dbHelper.getAllAssignedSubjects(((ApplicationContext) ViewAssignedSubjects.this.getApplicationContext()).getFacultyBean().getFacultyId()));
        subjectAdapter = new SubjectAdapter(this, subjectList);
        card_recyclerView.setAdapter(subjectAdapter);
    }

    private void initViews() {
        card_recyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}