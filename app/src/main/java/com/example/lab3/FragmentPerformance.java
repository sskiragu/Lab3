package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentPerformance extends Fragment {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    public String html ;
    public String  css ;
    public String javascript;

    View view;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_performance, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    html = userProfile.html;
                    css = userProfile.css;
                    javascript = userProfile.javascript;

                    initData();
                    initRecylerView();

                    Toast.makeText(getContext(), html.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    private void initData() {
        userList = new ArrayList<>();
        userList.add(new ModelClass(R.drawable.maixe, "HTML", "10:20AM", html, "_____________________________________________________________________"));
        userList.add(new ModelClass(R.drawable.rice, "CSS", "10:20AM", css, "_____________________________________________________________________"));
        userList.add(new ModelClass(R.drawable.others1, "JavaScript", "10:20AM", javascript, "_____________________________________________________________________"));
    }

    private void initRecylerView() {
        recyclerView = view.findViewById(R.id.myrecyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
