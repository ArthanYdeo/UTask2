package com.example.utask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_TOAST_SHOWN = "ToastShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "No name");

        // Check if the Toast message has been shown before
        boolean toastShown = sharedPreferences.getBoolean(KEY_TOAST_SHOWN, false);
        if (!toastShown) {
            // Display data using Toast
            Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();

            // Update SharedPreferences to mark the Toast message as shown
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_TOAST_SHOWN, true);
            editor.apply();
        }

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
    }
}
