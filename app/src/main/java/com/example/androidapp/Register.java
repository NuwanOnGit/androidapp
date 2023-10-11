package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    //Below method is used to check if user is already logged in or not also should Placed before onCreate() method
    //User is already logged in ? then redirect to main activity
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Loading Main Activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance(); // Initialize Firebase Auth
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);//text view


        //set on click listener for text view
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start login activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String email = editTextEmail.getText().toString().trim(); // trim() removes leading and trailing spaces
                String password = editTextPassword.getText().toString().trim();

                //checking if email or password is empty
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // set visibility to gone
                                if (task.isSuccessful()) {
                                    // Get the UID of the registered user
                                    String uid = mAuth.getCurrentUser().getUid();

                                    // Store user information under 'users' node with UID as the key
                                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                    usersRef.child("email").setValue(email);
                                    // You can add more user data to the database if needed
                                    usersRef.child("username").setValue("");
                                    usersRef.child("profilepicture").setValue("");
                                    usersRef.child("bio").setValue("");



                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                    // Start the login activity
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

//            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
//
//                String email = editTextEmail.getText().toString().trim(); // trim() removes leading and trailing spaces
//                String password = editTextPassword.getText().toString().trim();
//
//
//                //chechikng if email is empty
//                if(TextUtils.isEmpty(email)){
//                    Toast.makeText(Register.this, "Please enter email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //checking if password is empty
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(Register.this, "Please enter password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE); // set visibility to gone
//                                if (task.isSuccessful()) {
//
//                                    // Sign in success, update UI with the signed-in user's information
//                                    //Log.d(TAG, "createUserWithEmail:success");
//                                    //FirebaseUser user = mAuth.getCurrentUser();
//                                    //updateUI(user);
//
//                                    Toast.makeText(Register.this, "Account Created Successfully",
//                                            Toast.LENGTH_SHORT).show();
//                                    //start login activity
//                                    Intent intent = new Intent(getApplicationContext(), Login.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(Register.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    //updateUI(null);
//                                }
//                            }
//                        });
//            }
        });

    }
}