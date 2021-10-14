package com.workforashif.basicchatapp;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BasicChatApp extends Application {
    DatabaseReference reference;
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        try {
            Picasso.Builder builder = new Picasso.Builder(this);
//            builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
            Picasso built = builder.build();
            builder.indicatorsEnabled(true);
            built.setLoggingEnabled(true);
            Picasso.setSingletonInstance(built);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    reference.child("online").onDisconnect().setValue(false);
                    reference.child("online").setValue(true);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
