package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth; //auth
    Button button;
    TextView textView;
    FirebaseUser user;
    //Creating fab object
     FloatingActionButton fab;

    ActivityMainBinding binding;
    //@SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Bottom Navigation Bar code starts here
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Floating action button code starts here
        fab = findViewById(R.id.fab); // Assuming your FAB has the id 'fab' in your XML layout file
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start login activity
                Intent intent = new Intent(getApplicationContext(),AddEvents.class);
                startActivity(intent);
            }
        });


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    //finish();
                    return true;
                } else if (itemId == R.id.talent) {
                    startActivity(new Intent(getApplicationContext(), Talent.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.activities) {
                    startActivity(new Intent(getApplicationContext(), Activities.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

}