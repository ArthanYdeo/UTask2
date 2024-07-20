package com.example.utask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserInformation();
            }
        }, 2000);
    }

    private void checkUserInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);

        if (name != null) {
            // User information exists, go to homeActivity
            startActivity(new Intent(MainActivity.this, homeActivity.class));
        } else {
            // No user information, go to getInfo
            startActivity(new Intent(MainActivity.this, getInfo.class));
        }
        finish();
    }
}
