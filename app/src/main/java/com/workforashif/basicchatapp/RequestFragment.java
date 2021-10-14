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
import com.workforashif.basicchatapp.Model.RequestInfo;

public class RequestFragment extends Fragment {
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=view.findViewById(R.id.RequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        DatabaseReference mUsersDatabase1 = FirebaseDatabase.getInstance().getReference().child("Requests").child(mAuth.getCurrentUser().getUid());
        mUsersDatabase1.keepSynced(true);
        FirebaseRecyclerOptions<RequestInfo> options=new FirebaseRecyclerOptions.Builder<RequestInfo>().setQuery(mUsersDatabase1,RequestInfo.class).build();
        RequestAdapter requestAdapter=new RequestAdapter(options);
        requestAdapter.startListening();
        recyclerView.setAdapter(requestAdapter);
    }
}