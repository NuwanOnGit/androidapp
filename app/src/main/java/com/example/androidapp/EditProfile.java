package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private TextInputEditText textInputUserName,textInputBio,textInputExpertise,textInputContact,textInputLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Initialize TextInputEditText
        textInputUserName = findViewById(R.id.InputUserName);
        textInputBio = findViewById(R.id.textInputUserBio);
        textInputExpertise = findViewById(R.id.textInputUserExpertise);
        textInputContact = findViewById(R.id.textInputUserContactNumber);
        textInputLocation = findViewById(R.id.textInputUserLocation);

        // Get the current logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // The user is signed in
            String uid = currentUser.getUid();

            // Create a reference to the user's data in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            // Attach a ValueEventListener to retrieve data
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Handle data change
                    if (dataSnapshot.exists()) {
                        // Data for the current user exists

                        // Retrieve the expertise from the database
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        String userBio = dataSnapshot.child("userBio").getValue(String.class);
                        String userExpertise = dataSnapshot.child("userExpertise").getValue(String.class);
                        String userPhone = dataSnapshot.child("userPhone").getValue(String.class);
                        String userLocation = dataSnapshot.child("userLocation").getValue(String.class);

                        // Update the TextInputEditText with the retrieved expertise
                        textInputUserName.setText(userName);
                        textInputBio.setText(userBio);
                        textInputExpertise.setText(userExpertise);
                        textInputContact.setText(userPhone);
                        textInputLocation.setText(userLocation);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
            // Initialize btnSave button
            Button btnSave = findViewById(R.id.btnSave);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Update data in the database when btnSave is clicked
                    userRef.child("userName").setValue(textInputUserName.getText().toString());
                    userRef.child("userBio").setValue(textInputBio.getText().toString());
                    userRef.child("userExpertise").setValue(textInputExpertise.getText().toString());
                    userRef.child("userPhone").setValue(textInputContact.getText().toString());
                    userRef.child("userLocation").setValue(textInputLocation.getText().toString());

                    // You might want to add a success message or other handling here
                    // Success Toast Message
                    Toast.makeText(EditProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                    //Redirecting to Profile Page
                    finish();
                }
            });
        }
    }
}