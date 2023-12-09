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
import android.widget.Button;
import android.widget.LinearLayout;

import com.ams.studentattendance.R;
import com.ams.studentattendance.adapter.StudentAdapter;
import com.ams.studentattendance.adapter.StudentAdapterFaculty;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewStudentBean;
import com.ams.studentattendance.interfaces.OnClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentFaculty extends AppCompatActivity implements OnClickListener {

    private RecyclerView card_recyclerView;
    private StudentAdapterFaculty studentAdapter;
    private List<ViewStudentBean> studentBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("newText=", newText);
                studentAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("hasFocus=", String.valueOf(hasFocus));
            }
        });
        return true;
    }

    private void initViews() {
        card_recyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper dbHelper = new DatabaseHelper(ViewStudentFaculty.this);
        studentBeanList = new ArrayList<>(dbHelper.getAllStudent());
        studentAdapter = new StudentAdapterFaculty(this, studentBeanList, this);
        card_recyclerView.setAdapter(studentAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemClicked(ViewStudentBean studentBean) {
        Intent intent = new Intent(getApplicationContext(), StudentDetails.class);
        intent.putExtra("studentBean", studentBean);
        startActivity(intent);
    }
}