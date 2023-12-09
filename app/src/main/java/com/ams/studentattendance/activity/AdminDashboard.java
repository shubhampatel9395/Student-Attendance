package com.ams.studentattendance.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ams.studentattendance.R;
import com.ams.studentattendance.bean.UserDetailsBean;
import com.ams.studentattendance.context.ApplicationContext;
import com.ams.studentattendance.database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class AdminDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    LinearLayout profile;
    Button btnViewFaculty;
    Button btnAddFaculty;
	Button btnViewStudent;
    Button btnAddStudent;
    Button btnAddMapping;
    Button btnViewMapping;
    Button btnMarkAttendance, btnUploadAttendance, btnViewAttendance;
    Button btnViewProfile;
    Button btnLogout;
    TextView txtProfileName;
    TextView txtProfileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_admin_dashboard);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        profile = header.findViewById(R.id.profile);
        profile.setOnClickListener(this);
        txtProfileName = (TextView) header.findViewById(R.id.profilename);
        txtProfileEmail = (TextView) header.findViewById(R.id.profilemail);

        UserDetailsBean adminBean = ((ApplicationContext) AdminDashboard.this.getApplicationContext()).getAdminBean();
        if(adminBean != null) {
            txtProfileName.setText(adminBean.getFirstName() + " " + adminBean.getLastName());
            txtProfileEmail.setText(adminBean.getEmail());
        }

        btnViewFaculty = (Button) findViewById(R.id.btnViewFaculty);
        btnViewFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewFaculty.class);
                startActivity(intent);
            }
        });

        btnAddFaculty = (Button) findViewById(R.id.btnAddFaculty);
        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFaculty.class);
                startActivity(intent);
            }
        });
		
        btnViewStudent = (Button) findViewById(R.id.btnViewStudent);
        btnViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), ViewStudent.class);
                 startActivity(intent);
            }
        });

        btnAddStudent = (Button) findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                 startActivity(intent);
            }
        });

        btnAddMapping = (Button) findViewById(R.id.btnAddMapping);
        btnAddMapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFacultySubjectMapping.class);
                startActivity(intent);
            }
        });

        btnViewMapping = (Button) findViewById(R.id.btnViewMapping);
        btnViewMapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewFacultySubjectMapping.class);
                startActivity(intent);
            }
        });

        btnMarkAttendance = (Button) findViewById(R.id.btnMarkAttendance);
        btnMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MarkAttendance.class);
                startActivity(intent);
            }
        });

        btnUploadAttendance = (Button) findViewById(R.id.btnUploadAttendance);
        btnUploadAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadAttendance.class);
                startActivity(intent);
            }
        });

        btnViewAttendance  = (Button) findViewById(R.id.btnViewAttendance);
        btnViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewAttendance.class);
                startActivity(intent);
            }
        });

        btnViewProfile = (Button) findViewById(R.id.btnViewProfile);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewAdminProfile.class);
                startActivity(intent);
            }
        });

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_faculty) {
            Intent intent = new Intent(getApplicationContext(), AddFaculty.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_faculty) {
            Intent intent = new Intent(getApplicationContext(), ViewFaculty.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_student) {
            Intent intent = new Intent(getApplicationContext(), AddStudent.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_student) {
            Intent intent = new Intent(getApplicationContext(), ViewStudent.class);
            startActivity(intent);
        }else if (id == R.id.nav_assign_subject_faculty) {
            Intent intent = new Intent(getApplicationContext(), AddFacultySubjectMapping.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_subject_faculty) {
            Intent intent = new Intent(getApplicationContext(), ViewFacultySubjectMapping.class);
            startActivity(intent);
        } else if (id == R.id.nav_mark_attendance) {
            Intent intent = new Intent(getApplicationContext(), MarkAttendance.class);
            startActivity(intent);
        } else if (id == R.id.nav_upload_attendance) {
            Intent intent = new Intent(getApplicationContext(), UploadAttendance.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_attendance) {
            Intent intent = new Intent(getApplicationContext(), ViewAttendance.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_profile) {
            Intent intent = new Intent(getApplicationContext(), ViewAdminProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent =new Intent(getApplicationContext(), LoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}