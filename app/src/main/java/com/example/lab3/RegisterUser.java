package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fullNameEditText, ageEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        fullNameEditText = findViewById(R.id.fullNameTeditTextText);
        ageEditText = findViewById(R.id.ageEditText);
        emailEditText = findViewById(R.id.emailRegisterEditText);
        passwordEditText = findViewById(R.id.passwordRegistereditText);
    }

    public void registerUser(View v){
        String fullName = fullNameEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if(fullName.isEmpty()){
            fullNameEditText.setError("Name is required");
            fullNameEditText.requestFocus();
            return;
        }

        if(age.isEmpty()){
            ageEditText.setError("Age is required");
            ageEditText.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Provide a valid email!");
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordEditText.setError("Min password length should be 8 characters");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, age, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                        //Progress bar

                                        //Redirect to login page
                                    }else{
                                        Toast.makeText(RegisterUser.this,"Failed to register user! Try again Later", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            fullNameEditText.getText().clear();
                            ageEditText.getText().clear();
                            emailEditText.getText().clear();
                            passwordEditText.getText().clear();
                        }else {
                            Toast.makeText(RegisterUser.this,"Failed to register user! Try again Later", Toast.LENGTH_LONG).show();
                        }
                    }
                });


//        Toast.makeText(getApplicationContext(), "Full Name: " + fullName + " \n Age: " + age + "\n Email: " + email + "\n Password " + password, Toast.LENGTH_LONG).show();
    }
}