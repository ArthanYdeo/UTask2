package com.example.utask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class createActivity extends AppCompatActivity {

    private EditText taskTitle, taskDetails;
    private TextView taskTimeline;
    private Button saveButton;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Initialize UI components
        taskTitle = findViewById(R.id.taskTitle);
        taskDetails = findViewById(R.id.taskDetails);
        taskTimeline = findViewById(R.id.taskTimeline);
        saveButton = findViewById(R.id.saveButton);

        // Setup Date and Time Picker
        taskTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        // Handle save button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = taskTitle.getText().toString();
                String details = taskDetails.getText().toString();
                String timeline = taskTimeline.getText().toString();

                // Validate input
                if (title.isEmpty() || details.isEmpty() || timeline.isEmpty()) {
                    Toast.makeText(createActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save task data in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("taskTitle", title);
                editor.putString("taskDetails", details);
                editor.putString("taskTimeline", timeline);
                editor.apply();

                // Clear the fields
                taskTitle.setText("");
                taskDetails.setText("");
                taskTimeline.setText("");

                // Notify user and return to homeActivity
                Toast.makeText(createActivity.this, "Task saved!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(createActivity.this, homeActivity.class));
                finish(); // Optionally finish this activity
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.create);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.complete:
                        startActivity(new Intent(getApplicationContext(), completeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.create:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), homeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            // Show TimePickerDialog after selecting the date
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                mHour = hourOfDay;
                mMinute = minute;
                // Update the TextView with the selected date and time
                taskTimeline.setText(String.format("%02d/%02d/%d %02d:%02d", mDay, mMonth + 1, mYear, mHour, mMinute));
            }, mHour, mMinute, true);
            timePickerDialog.show();
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
