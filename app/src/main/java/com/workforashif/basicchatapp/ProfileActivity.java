package com.workforashif.basicchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageView;
    TextView status,name;
    Button sendReqButton;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    static int NOT_FRIEND=0;
    static int REQUEST_SENT=1;
    static int REQUEST_RECIEVED=2;
    static int FRIENDS=3;
    static int current =NOT_FRIEND;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView=findViewById(R.id.imageView);
        status=findViewById(R.id.statusTextView);
        name=findViewById(R.id.usernameTextView);
        sendReqButton=findViewById(R.id.sendreqButton);
        sendReqButton.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fuser=mAuth.getCurrentUser();
        String myuser=fuser.getUid();
        Intent intent= getIntent();
        String nameS=intent.getStringExtra("name");
        String statusS=intent.getStringExtra("status");
        String imageS=intent.getStringExtra("image");
        final String otheruser=intent.getStringExtra("uid");
         current=0;


        reference= FirebaseDatabase.getInstance().getReference()
                .child("Requests").child(myuser).child(otheruser);

        if(!imageS.equals("default"))
            Picasso.with(getApplicationContext()).load(imageS).into(imageView);
        status.setText(statusS);
        name.setText(nameS);
        sendReqButton.setEnabled(false);
        sendReqButton.setVisibility(View.INVISIBLE);

        checkforFRIEND_REQUEST_SENT();
        if(current==NOT_FRIEND)
            checkforFriend(myuser,otheruser);
        if(current==NOT_FRIEND)
            sendReqButton.setText("Send Friend Request");
        else if(current==REQUEST_RECIEVED)
            sendReqButton.setText("Accept Friend Request");
        else if(current==REQUEST_SENT)
            sendReqButton.setText("Cancel Friend Request");
        else
            sendReqButton.setText("UnFriend This Person");
        sendReqButton.setVisibility(View.VISIBLE);
        System.out.println(current);

        /*checkIfAlreadyFriend(fuser.getUid(),uidS);
        checkifRequestSentorRecieved();

        if(current==0)
            sendReqButton.setText("Send Friend Request");
        else if(current==REQUEST_SENT)
            sendReqButton.setText("Cancel Friend Request");
        else if(current==REQUEST_RECIEVED)
            sendReqButton.setText("Accept Friend Request");
        else
            sendReqButton.setText("UnFriend This Person");
        sendReqButton.setEnabled(true);
        sendReqButton.setVisibility(View.VISIBLE);
        System.out.println(current);*/

        sendReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonClick(fuser.getUid(),otheruser);
            }
        });
    }

    private void checkforFRIEND_REQUEST_SENT() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*if(snapshot!=null){
                    if(snapshot.child("type").getValue().equals(REQUEST_SENT))
                        current=REQUEST_SENT;
                    else{
                        current=REQUEST_RECIEVED;
                    }

                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkforFriend(String myuser,String otheruser){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Friends").child(myuser).child(otheruser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null)
                    current=FRIENDS;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   /* private void checkifRequestSentorRecieved() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("type").getValue()==null){

                }

                else if(dataSnapshot.child("type").getValue().toString().equals("sent")) {
                    current = REQUEST_SENT;
                }
                else {
                    current = REQUEST_RECIEVED;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


    private void checkIfAlreadyFriend(final String otheruid,  String myuid) {
        System.out.println(otheruid);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Friends").child(myuid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getKey());
                    if (dataSnapshot.getKey().equals(otheruid)) {
                        current=FRIENDS;
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void ButtonClick(String myuid, String otheruid){

    }


}