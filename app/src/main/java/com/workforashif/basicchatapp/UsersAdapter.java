package com.workforashif.basicchatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.workforashif.basicchatapp.Model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends FirebaseRecyclerAdapter<UserInfo, UsersAdapter.UserAdapterViewHolder> {
    Context context;
    private FirebaseAuth mAuth;
    List<UserInfo> userList;
    List<String> keys;
    public UsersAdapter(@NonNull FirebaseRecyclerOptions<UserInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final UserAdapterViewHolder holder, final int i, final UserInfo userInfo) {
        holder.status.setText(userInfo.getStatus());
        holder.username.setText(userInfo.getName());
        if(!userInfo.getImage().equals("default"))
            Picasso.with(context).load(userInfo.getImage()).into(holder.imageView);
        DatabaseReference mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> list = dataSnapshot.getChildren();

                // Filter current user
                userList = new ArrayList<>();
                keys=new ArrayList<>();
                for (DataSnapshot user : list) {
                    keys.add(user.getKey());
                    userList.add(user.getValue(UserInfo.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth=FirebaseAuth.getInstance();
                Intent intent=new Intent(context,ProfileActivity.class);
                intent.putExtra("name",userInfo.getName());
                intent.putExtra("status",userInfo.getStatus());
                intent.putExtra("image",userInfo.getImage());
                intent.putExtra("uid",keys.get(i));
                context.startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_layout,parent,false);
        context=parent.getContext();
        return new UserAdapterViewHolder(view);
    }

    class UserAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView username,status;
        CircleImageView imageView;

        public UserAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.usernameTextView);
            status=itemView.findViewById(R.id.userSatus);
            imageView= itemView.findViewById(R.id.profile_imageView);

        }
    }
}
