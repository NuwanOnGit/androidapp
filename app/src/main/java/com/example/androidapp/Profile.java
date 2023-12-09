package com.example.androidapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    FirebaseAuth mAuth; //auth
    Button button;
    TextView textView,editProfileTextView,userNameTextView,userBioTextView,userExpertiseTextView,userPhoneTextView,userLocationTextView;
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
        editProfileTextView = findViewById(R.id.editProfile);
        userNameTextView = findViewById(R.id.textView3);
        userBioTextView = findViewById(R.id.textView6);
        userExpertiseTextView = findViewById(R.id.textView7);
        userPhoneTextView = findViewById(R.id.textView2);
        userLocationTextView = findViewById(R.id.textViewLocation);

        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(user.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle data change
                if (dataSnapshot.exists()) {
                    // Data for the current user exists

                    // Retrieve and display data in your TextViews
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String userBio = dataSnapshot.child("userBio").getValue(String.class);
                    String userExpertise = dataSnapshot.child("userExpertise").getValue(String.class);
                    String userPhone = dataSnapshot.child("userPhone").getValue(String.class);
                    String userLocation = dataSnapshot.child("userLocation").getValue(String.class);


                    // Update your TextViews with the retrieved data
                    userNameTextView.setText(userName);
                    userBioTextView.setText(userBio);
                    userExpertiseTextView.setText(userExpertise);
                    userPhoneTextView.setText(userPhone);
                    userLocationTextView.setText(userLocation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e(TAG, "Error reading user data", databaseError.toException());
            }
        });



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

        TextView editProfileTextView = findViewById(R.id.editProfile);

        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the activity here
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        //redirecting to published event
        TextView textView9 = findViewById(R.id.textView9);

        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the activity to PublishedEventsActivity
                Intent intent = new Intent(Profile.this, PublishedEvents.class);
                startActivity(intent);
            }
        });


        //redirecting to joined events

        TextView textView10 = findViewById(R.id.textView10);

        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the activity to PublishedEventsActivity
                Intent intent = new Intent(Profile.this, JoinedEvents.class);
                startActivity(intent);
            }
        });




        //on create block ends here
    }
}