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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {


    FirebaseAuth mAuth; //auth
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Authenticating user code starts here
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        button = findViewById(R.id.btn_logout);
        textView = findViewById(R.id.textView);
        user = mAuth.getCurrentUser();

        if (user == null) {
            //user is not logged in
            textView.setText("User is not logged in");
            //start login activity
            Intent intent = new Intent(getApplicationContext(), Login.class);
            finish();
        } else {
            //user is logged in
            textView.setText(user.getEmail());//get user email
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                finish();
            }
        });
        //Authenticating user code ends here


        //Bottom Navigation Bar code starts here
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.profile) {
                    finish();
                    return true;
                } else if (itemId == R.id.talent) {
                    startActivity(new Intent(getApplicationContext(), Talent.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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