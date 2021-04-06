package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentProfile extends Fragment {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    EditText fullNameEditText, emailEditText, ageEditText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        fullNameEditText = view.findViewById(R.id.fullnameProfileEditText);
        emailEditText = view.findViewById(R.id.emailProfileEditText);
        ageEditText = view.findViewById(R.id.ageProfileEditText);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String  email = userProfile.email;
                    String age = userProfile.age;

                    fullNameEditText.setText(fullName);
                    emailEditText.setText(email);
                    ageEditText.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
//        String fullName = bundle.getString("fn");
//        String email = bundle.getString("em");
//        String age = bundle.getString("ag");
        return view;
    }
}
