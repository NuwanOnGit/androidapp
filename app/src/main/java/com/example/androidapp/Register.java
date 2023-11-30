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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword,editTextUserName;
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
        editTextUserName = findViewById(R.id.userName);
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
                //progressBar.setVisibility(View.VISIBLE);

                String email = editTextEmail.getText().toString().trim(); // trim() removes leading and trailing spaces
                String password = editTextPassword.getText().toString().trim();
                String userName = editTextUserName.getText().toString().trim();

                TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);
                TextInputLayout passwordInputLayout = findViewById(R.id.passwordInputLayout);
                TextInputLayout usernameInputLayout = findViewById(R.id.usernameInputLayout);

                // Checking if email is empty and matches the valid email pattern
                if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    emailInputLayout.setError("Please enter a valid email address");
                    return;
                } else {
                    emailInputLayout.setError(null); // Clear the error
                }

                // Checking if password is empty and matches the valid password pattern
                if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
                    passwordInputLayout.setError("Password must be at least 8 characters long and contain one digit, one capital letter, and one special character");
                    return;
                } else {
                    passwordInputLayout.setError(null); // Clear the error
                }
                // Validate username
                if (TextUtils.isEmpty(userName) || !isValidUsername(userName)) {
                    usernameInputLayout.setError("Username must contain at least 4 letters");
                    return;
                } else {
                    usernameInputLayout.setError(null); // Clear the error
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
                                    usersRef.child("userEmail").setValue(email);
                                    // You can add more user data to the database if needed
                                    usersRef.child("userName").setValue(userName);
                                    usersRef.child("userDp").setValue("");
                                    usersRef.child("userBio").setValue("");
                                    usersRef.child("userPhone").setValue("");
                                    usersRef.child("userLocation").setValue("");
                                    usersRef.child("userExpertise").setValue("");
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

            // Email validation using regex
            private boolean isValidEmail(CharSequence target) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
            // Password validation using regex
            private boolean isValidPassword(CharSequence password) {
                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
                // Password should contain at least one digit, one capital letter, one special character,
                // no whitespace, and at least 8 characters long
                return Pattern.compile(passwordPattern).matcher(password).matches();
            }
            // Username validation using regex
            private boolean isValidUsername(String username) {
                // Username must contain at least 4 characters and only capital and simple letters are allowed
                String usernamePattern = "^[a-zA-Z\\s]{4,}$";
                return Pattern.compile(usernamePattern).matcher(username).matches();
            }
        });
    }
}