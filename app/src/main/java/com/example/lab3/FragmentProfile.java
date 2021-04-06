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

public class FragmentProfile extends Fragment {
    EditText fullNameEditText, emailEditText, ageEditText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        fullNameEditText = view.findViewById(R.id.fullnameProfileEditText);
        emailEditText = view.findViewById(R.id.emailProfileEditText);
        ageEditText = view.findViewById(R.id.ageProfileEditText);
        String fullName = bundle.getString("fn");
        String email = bundle.getString("em");
        String age = bundle.getString("ag");
        fullNameEditText.setText(fullName);
        emailEditText.setText(email);
        ageEditText.setText(age);
        return view;
    }
}
