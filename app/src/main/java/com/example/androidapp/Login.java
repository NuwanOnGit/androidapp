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

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;//Changed object name
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
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance(); // Initialize Firebase Auth
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);//text view


        //set on click listener for text view
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start login activity
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);

                String email = editTextEmail.getText().toString().trim(); // trim() removes leading and trailing spaces
                String password = editTextPassword.getText().toString().trim();

                TextInputLayout emailInputLayout = findViewById(R.id.textInputLayout);
                TextInputLayout passwordInputLayout = findViewById(R.id.textInputLayout2);

                //chechikng if email is empty
//                if(TextUtils.isEmpty(email)){
//                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //checking if password is empty
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                // Checking if email is empty and matches the valid email pattern
                if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    emailInputLayout.setError("Please enter a valid email address");
                    return;
                } else {
                    emailInputLayout.setError(null); // Clear the error
                }

                // Checking if password is empty and matches the valid password pattern
//                if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
//                    passwordInputLayout.setError("Password Incorrect");
//                    return;
//                } else {
//                    passwordInputLayout.setError(null); // Clear the error
//                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // set visibility to gone
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                    Toast.makeText(Login.this, "Login Successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            // Email validation using regex
            private boolean isValidEmail(CharSequence target) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
            // Password validation using regex
//            private boolean isValidPassword(CharSequence password) {
//                String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
//                // Password should contain at least one digit, one capital letter, one special character,
//                // no whitespace, and at least 8 characters long
//                return Pattern.compile(passwordPattern).matcher(password).matches();
//            }
        });

    }
}