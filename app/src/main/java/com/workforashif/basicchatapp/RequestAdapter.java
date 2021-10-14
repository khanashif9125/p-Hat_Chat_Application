package com.workforashif.basicchatapp;

import android.content.Context;
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
import com.workforashif.basicchatapp.Model.RequestInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends FirebaseRecyclerAdapter<RequestInfo, RequestAdapter.RequestAdapterViewHolder> {
    Context context;
    private FirebaseAuth mAuth;


    public RequestAdapter(@NonNull FirebaseRecyclerOptions<RequestInfo> options) {
        super(options);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull final RequestAdapter.RequestAdapterViewHolder holder, int i, RequestInfo requestInfo) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(requestInfo.getKey());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name =snapshot.child("name").getValue().toString();
                String status=snapshot.child("status").getValue().toString();
                String image=snapshot.child("image").getValue().toString();
                String thumbnil=snapshot.child("thumbnil").getValue().toString();
                String uid=snapshot.getKey();

                holder.status.setText(status);
                holder.username.setText(name);
                if(!image.equals("default"))
                    Picasso.with(context).load(image).into(holder.imageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @NonNull
    @Override
    public RequestAdapter.RequestAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_request_item,parent,false);
        context=parent.getContext();

        return new RequestAdapter.RequestAdapterViewHolder(view);
    }

    class RequestAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView username,status;
        CircleImageView imageView;

        public RequestAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.usernameTextView);
            status=itemView.findViewById(R.id.userSatus);
            imageView= itemView.findViewById(R.id.profile_imageView);

        }
    }


}
