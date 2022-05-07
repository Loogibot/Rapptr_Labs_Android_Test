package com.datechnologies.androidtest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // remove appbar on top for splash screen
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { // switches to MainActivity after delay
                startActivity(new Intent(splash_activity.this, MainActivity.class));
                finish();
            }
        },1000); // delay in milliseconds
    }
}