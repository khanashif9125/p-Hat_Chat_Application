package com.workforashif.basicchatapp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText message;
    ImageButton sendButton,addButton;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView=findViewById(R.id.recyclerview);
        toolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        String username=getIntent().getStringExtra("username");
        String image=getIntent().getStringExtra("image");
        Boolean seen=getIntent().getBooleanExtra("online",false);
        TextView textView=findViewById(R.id.Toolbarusername);
        textView.setText(username);
        ImageView imageView=findViewById(R.id.chat_image);
        TextView seentextImage=findViewById(R.id.lastseen);
        if(!image.equals("default"))
            Picasso.with(getApplicationContext()).load(image).into(imageView);
        textView.setText(username);
        if(seen)
            seentextImage.setVisibility(View.VISIBLE);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View charbar=inflater.inflate(R.layout.chat_toolbar,null);
        actionBar.setCustomView(charbar);
        message=findViewById(R.id.messageText);
        sendButton=findViewById(R.id.sendButton);
        addButton=findViewById(R.id.addButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {

        String otherUid =getIntent().getStringExtra("uid");
        String myuid=getIntent().getStringExtra("myuid");
         String msg = message.getText().toString();
         DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
         if(TextUtils.isEmpty(msg))
             message.setError("Enter something!!");
         else{
             String curr_user="messages/"+myuid+"/"+otherUid;
             String other_user="messages/"+otherUid+"/"+myuid;
             reference.child("messages").child(myuid).child(other_user);
             Map map=new HashMap();
             map.put("message",message);
             map.put("type","send");
             map.put("seen",false);

             Map user= new HashMap();
//             user.put(myuid);


         }
    }
}