package com.example.utask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "No name");
        String email = sharedPreferences.getString("email", "No email");
        String school = sharedPreferences.getString("school", "No school");
        String num = sharedPreferences.getString("num", "No number");
        String studNum = sharedPreferences.getString("studNum", "No student number");

        // Display data using Toast
        Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();

        //bottom nav//
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), createActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.complete:
                        startActivity(new Intent(getApplicationContext(), completeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Update TextViews with user data
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView nameTextView = findViewById(R.id.nameTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView emailTextView = findViewById(R.id.emailTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView schoolTextView = findViewById(R.id.schoolTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView numTextView = findViewById(R.id.numTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView studNumTextView = findViewById(R.id.studNumTextView);

        nameTextView.setText(name);
        emailTextView.setText(email);
        schoolTextView.setText(school);
        numTextView.setText(num);
        studNumTextView.setText(studNum);
    }
}