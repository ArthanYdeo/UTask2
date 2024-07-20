package com.example.utask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "No name");
        String email = sharedPreferences.getString("email", "No email");
        String school =sharedPreferences.getString("school", "No school");
        String number = sharedPreferences.getString("num", "No number");
        String studNum = sharedPreferences.getString("studNum", "No student number");

        // Update TextView with user name
        TextView profileNameTextView = findViewById(R.id.profileNameTextView);
        profileNameTextView.setText(name);

        //Email//
        TextView profileEmailTextView = findViewById(R.id.profileEmailTextView);
        profileEmailTextView.setText(email);

        //School//
        TextView profileSchoolTextView = findViewById(R.id.profileSchoolTextView);
        profileSchoolTextView.setText(school);

        //Number//
        TextView profileNumberTextView = findViewById(R.id.profileNumberTextView);
        profileNumberTextView.setText(number);

        //StudNumber//
        TextView profileStudNumberTextView = findViewById(R.id.profileStudNumberTextView);
        profileStudNumberTextView.setText(studNum);


        // Logout button functionality
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Redirect to login activity
                Intent intent = new Intent(profileActivity.this, welcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // Bottom navigation functionality
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), homeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
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
    }
}
