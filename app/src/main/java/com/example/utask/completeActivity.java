package com.example.utask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class completeActivity extends AppCompatActivity {

    private LinearLayout completedTasksContainer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        completedTasksContainer = findViewById(R.id.completedTasksContainer);

        // Fetch and display completed tasks
        fetchAndDisplayCompletedTasks();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.complete);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), homeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.complete:
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), createActivity.class));
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

    private void fetchAndDisplayCompletedTasks() {
        SharedPreferences completedTaskPreferences = getSharedPreferences("CompletedTaskPrefs", MODE_PRIVATE);
        // Iterate through all tasks in SharedPreferences
        for (String key : completedTaskPreferences.getAll().keySet()) {
            String task = completedTaskPreferences.getString(key, "");
            String[] taskDetails = task.split("\\|");

            if (taskDetails.length == 3) {
                addTaskToContainer(taskDetails[0], taskDetails[1], taskDetails[2]);
            }
        }
    }

    private void addTaskToContainer(String title, String details, String timeline) {
        // Inflate task layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View taskView = inflater.inflate(R.layout.task_item, completedTasksContainer, false);

        // Find views in the task layout
        TextView taskTitle = taskView.findViewById(R.id.taskTitle);
        TextView taskDetails = taskView.findViewById(R.id.taskDetails);
        TextView taskTimeline = taskView.findViewById(R.id.taskTimeline);

        // Set task data
        taskTitle.setText("Title: " + title);
        taskDetails.setText("Details: " + details);
        taskTimeline.setText("Timeline: " + timeline);

        // Add the task view to the container
        completedTasksContainer.addView(taskView);
    }
}
