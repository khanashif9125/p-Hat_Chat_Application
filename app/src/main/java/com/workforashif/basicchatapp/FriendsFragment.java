package com.workforashif.basicchatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.workforashif.basicchatapp.Model.FriendsInfo;

public class FriendsFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_friends, container, false);
       
        recyclerView=view.findViewById(R.id.friendsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();

        DatabaseReference mUsersDatabase1 = FirebaseDatabase.getInstance().getReference().child("Friends").child(mAuth.getCurrentUser().getUid());
        mUsersDatabase1.keepSynced(true);
        FirebaseRecyclerOptions<FriendsInfo> options=new FirebaseRecyclerOptions.Builder<FriendsInfo>().setQuery(mUsersDatabase1,FriendsInfo.class).build();
        FriendsAdapter friendsAdapter=new FriendsAdapter(options);
        friendsAdapter.startListening();
        recyclerView.setAdapter(friendsAdapter);
    }


}