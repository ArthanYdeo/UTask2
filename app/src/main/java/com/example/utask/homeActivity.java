package com.example.utask;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //bottom nav//
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case  R.id.create:
                        startActivity(new Intent(getApplicationContext(),createActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.complete:
                        startActivity(new Intent(getApplicationContext(),completeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });





        //bundle shit//
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String name = bundle.getString("name");
            String email = bundle.getString("email");
            String school_name = bundle.getString("univ_name");
            String contact_num = bundle.getString("contact_num");
            String student_num = bundle.getString("stud_num");
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        }

    }
}