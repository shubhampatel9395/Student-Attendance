package com.ams.studentattendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ams.studentattendance.R;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_splash_screen);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtView = (TextView) findViewById(R.id.textView);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imageView.setAnimation(topAnim);
        txtView.setAnimation(bottomAnim);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        timerThread.start();
    }
}