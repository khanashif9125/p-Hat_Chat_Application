package com.workforashif.basicchatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.workforashif.basicchatapp.Model.UserInfo;

public class All_UsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__users);

        recyclerView = findViewById(R.id.allUserRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.allUserToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);
        FirebaseRecyclerOptions<UserInfo> options=new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(mUsersDatabase,UserInfo.class).build();
        UsersAdapter usersAdapter=new UsersAdapter(options);
        usersAdapter.startListening();
        recyclerView.setAdapter(usersAdapter);
    }

}