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
import com.ams.studentattendance.adapter.FacultySubjectMappingAdapter;
import com.ams.studentattendance.database.DatabaseHelper;
import com.ams.studentattendance.dto.ViewFacultyBean;
import com.ams.studentattendance.dto.ViewFacultySubjectMappingBean;
import com.ams.studentattendance.interfaces.OnClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ViewFacultySubjectMapping extends AppCompatActivity implements OnClickListener {
    private RecyclerView card_recyclerView;
    private FacultySubjectMappingAdapter adapter;
    private List<ViewFacultySubjectMappingBean> mappingList;
    private LinearLayout linearRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty_subject_mapping);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Subject's Faculty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
                adapter.getFilter().filter(newText);
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

    private void loadData() {
        DatabaseHelper dbHelper = new DatabaseHelper(ViewFacultySubjectMapping.this);
        mappingList = new ArrayList<>(dbHelper.getAllFacultySubjectMappings());
        adapter = new FacultySubjectMappingAdapter(this, mappingList, this);
        card_recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        card_recyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        card_recyclerView.setLayoutManager(layoutManager);

        linearRow = (LinearLayout) findViewById(R.id.linearRow);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(card_recyclerView);
    }

    @Override
    public void onItemClicked(ViewFacultySubjectMappingBean bean) {
        Intent intent = new Intent(getApplicationContext(), FacultySubjectMappingDetails.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DatabaseHelper db = new DatabaseHelper(ViewFacultySubjectMapping.this);
            final int position = viewHolder.getAdapterPosition();
            db.deleteFacultySubjectMapping(adapter.getfMappingList().get(position));
            adapter.removeItem(position);
            Snackbar.make(findViewById(R.id.card_recyclerView), "Mapping Deleted!!!", Snackbar.LENGTH_LONG).show();
        }
    };
}