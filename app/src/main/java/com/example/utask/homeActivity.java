package com.example.utask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_TOAST_SHOWN = "ToastShown";
    private static final String TASK_PREFS_NAME = "TaskPrefs";
    private static final String KEY_TASK_TITLE = "taskTitle";
    private static final String KEY_TASK_DETAILS = "taskDetails";
    private static final String KEY_TASK_TIMELINE = "taskTimeline";

    private LinearLayout taskContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        taskContainer = findViewById(R.id.taskContainer);

        // Retrieve data from SharedPreferences for user info
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

        // Retrieve task data from SharedPreferences
        SharedPreferences taskPreferences = getSharedPreferences(TASK_PREFS_NAME, MODE_PRIVATE);
        String title = taskPreferences.getString(KEY_TASK_TITLE, "No title");
        String details = taskPreferences.getString(KEY_TASK_DETAILS, "No details");
        String timeline = taskPreferences.getString(KEY_TASK_TIMELINE, "No timeline");

        // Add task to the container
        addTaskToContainer(title, details, timeline);

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

    private void addTaskToContainer(String title, String details, String timeline) {
        // Inflate task layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View taskView = inflater.inflate(R.layout.task_item, taskContainer, false);

        // Find views in the task layout
        TextView taskTitle = taskView.findViewById(R.id.taskTitle);
        TextView taskDetails = taskView.findViewById(R.id.taskDetails);
        TextView taskTimeline = taskView.findViewById(R.id.taskTimeline);
        Button completeButton = taskView.findViewById(R.id.completeTaskButton);

        // Set task data
        taskTitle.setText("Title: " + title);
        taskDetails.setText("Details: " + details);
        taskTimeline.setText("Timeline: " + timeline);

        // Handle button click
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the completed task
                SharedPreferences completedTaskPreferences = getSharedPreferences("CompletedTaskPrefs", MODE_PRIVATE);
                SharedPreferences.Editor completedTaskEditor = completedTaskPreferences.edit();

                // Create a unique key for each task
                String uniqueKey = "task_" + System.currentTimeMillis();
                completedTaskEditor.putString(uniqueKey, title + "|" + details + "|" + timeline);
                completedTaskEditor.apply();

                // Remove the task from current tasks
                SharedPreferences.Editor taskEditor = getSharedPreferences(TASK_PREFS_NAME, MODE_PRIVATE).edit();
                taskEditor.remove(KEY_TASK_TITLE);
                taskEditor.remove(KEY_TASK_DETAILS);
                taskEditor.remove(KEY_TASK_TIMELINE);
                taskEditor.apply();

                // Notify the user
                Toast.makeText(homeActivity.this, "Task completed and moved to completed tasks!", Toast.LENGTH_SHORT).show();

                // Optionally, remove the task view
                taskContainer.removeView(taskView);
            }
        });

        // Add the task view to the container
        taskContainer.addView(taskView);
    }
}
