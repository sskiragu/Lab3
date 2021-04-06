package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText emailLoginEditText, passwordLoginEditText;
    private ProgressBar loginProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailLoginEditText = findViewById(R.id.emailLoginEditText);
        passwordLoginEditText = findViewById(R.id.passwordLoginEditText);
        loginProgressBar = findViewById(R.id.loginProgressBar);
    }

    public void signUpPage(View v){
        Intent intent = new Intent(MainActivity.this, RegisterUser.class);
        startActivity(intent);
    }

    public void forgotPassword(View v){
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void loginUser(View v){
        String email = emailLoginEditText.getText().toString().trim();
        String password = passwordLoginEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailLoginEditText.setError("Email is required");
            emailLoginEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLoginEditText.setError("Provide valid email!");
            emailLoginEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordLoginEditText.setError("Password is required");
            passwordLoginEditText.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordLoginEditText.setError("Min password length should be 8 characters");
            passwordLoginEditText.requestFocus();
            return;
        }
        loginProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to profile activity
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}