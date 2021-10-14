package com.workforashif.basicchatapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.workforashif.basicchatapp.Model.FriendsInfo;
import com.workforashif.basicchatapp.Model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends FirebaseRecyclerAdapter<FriendsInfo, FriendsAdapter.FriendAdapterViewHolder> {
    Context context;
    private FirebaseAuth mAuth;
    static List<UserInfo> list;
    static List<String> keys;
     static int j=0,k=0;
     static FriendAdapterViewHolder holder1;
    String name,status,image,thumbnil,uid;
    Boolean online_Status;

    public FriendsAdapter(@NonNull FirebaseRecyclerOptions<FriendsInfo> options) {
        super(options);
        mAuth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        keys=new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull final FriendsAdapter.FriendAdapterViewHolder holder, int i, FriendsInfo friendsInfo) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(friendsInfo.getKey());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAuth=FirebaseAuth.getInstance();
                name =snapshot.child("name").getValue().toString();
                status=snapshot.child("status").getValue().toString();
               image=snapshot.child("image").getValue().toString();
                thumbnil=snapshot.child("thumbnil").getValue().toString();
                uid=snapshot.getKey();
                online_Status= (Boolean) snapshot.child("online").getValue();

                holder.status.setText(status);
                holder.username.setText(name);
                if(online_Status)
                    holder.online.setVisibility(View.VISIBLE);
                else
                    holder.online.setVisibility(View.INVISIBLE);
                if(!image.equals("default"))
                    Picasso.with(context).load(image).into(holder.imageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth=FirebaseAuth.getInstance();
                Intent intent=new Intent(context,ChatActivity.class);
                intent.putExtra("username",name);
                intent.putExtra("status",status);
                intent.putExtra("image",image);
                intent.putExtra("uid",uid);
                intent.putExtra("online",online_Status);
                intent.putExtra("myuid",mAuth.getCurrentUser().toString());
                System.out.println(mAuth.getCurrentUser().toString()+" "+uid);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public FriendsAdapter.FriendAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_layout,parent,false);
        context=parent.getContext();

        return new FriendsAdapter.FriendAdapterViewHolder(view);
    }

    class FriendAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView username,status;
        CircleImageView imageView;
        ImageView online;

        public FriendAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.usernameTextView);
            status=itemView.findViewById(R.id.userSatus);
            imageView= itemView.findViewById(R.id.profile_imageView);
            online=itemView.findViewById(R.id.onlineImage);
        }
    }


}

