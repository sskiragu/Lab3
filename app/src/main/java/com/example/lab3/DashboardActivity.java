package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

//    private FirebaseUser user;
//    private DatabaseReference reference;
//    private String userID;

    private EditText fullNameProfileEditText, emailProfileEditText, ageProfileEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        final FragmentProfile fragmentProfile = new FragmentProfile();

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();

        fullNameProfileEditText = findViewById(R.id.fullnameProfileEditText);
        emailProfileEditText = findViewById(R.id.emailProfileEditText);
        ageProfileEditText = findViewById((R.id.ageProfileEditText));

        BottomNavigationView stdNav = findViewById(R.id.student_navigation);
        stdNav.setOnNavigationItemSelectedListener(navListener);

//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//                if(userProfile != null){
//                   String fullName = userProfile.fullName;
//                   String  email = userProfile.email;
//                   String age = userProfile.age;
//                   Bundle bundle = new Bundle();
//                   bundle.putString("fn", fullName);
//                   bundle.putString("em", email);
//                   bundle.putString("ag", age);
//                   fragmentProfile.setArguments(bundle);
//                   fragmentTransaction.replace(R.id.fragment_container, fragmentProfile);
//                   fragmentTransaction.commit();
//
//
////                    fullNameProfileEditText.setText(fullName);
////                    emailProfileEditText.setText(email);
////                    ageProfileEditText.setText(age);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(DashboardActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_profile:
                            selectedFragment = new FragmentProfile();
                            break;
                        case R.id.nav_marks:
                            selectedFragment = new FragmentPerformance();
                            break;
                        case R.id.nav_fees:
                            selectedFragment = new FragmentFees();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


}