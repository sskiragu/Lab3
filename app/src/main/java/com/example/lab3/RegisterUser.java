package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class RegisterUser extends AppCompatActivity implements LocationListener {

    private FirebaseAuth mAuth;
    LocationManager locationManager;
    private EditText fullNameEditText, ageEditText, emailEditText, passwordEditText, countryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        fullNameEditText = findViewById(R.id.fullNameTeditTextText);
        ageEditText = findViewById(R.id.ageEditText);
        emailEditText = findViewById(R.id.emailRegisterEditText);
        passwordEditText = findViewById(R.id.passwordRegistereditText);
        countryEditText = findViewById(R.id.countryEditText);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();
    }

    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(RegisterUser.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//            tvCity.setText(addresses.get(0).getLocality());
//            tvState.setText(addresses.get(0).getAdminArea());
            countryEditText.setText(addresses.get(0).getCountryName());
//            tvPin.setText(addresses.get(0).getPostalCode());
//            tvLocality.setText(addresses.get(0).getAddressLine(0));

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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