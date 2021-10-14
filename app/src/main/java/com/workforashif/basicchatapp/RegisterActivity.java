package com.workforashif.basicchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    TextInputEditText displayName,emailEditText,passwordEditText;
    Button regBtn;
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        displayName=findViewById(R.id.displayName);
        emailEditText=findViewById(R.id.EmailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        regBtn=findViewById(R.id.reg_btn);

        toolbar=findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(displayName.getText().toString().length()==0)
                    displayName.setError("Display name required");
                else if(emailEditText.getText().toString().length()==0)
                    emailEditText.setError("Email address required");
                else if(passwordEditText.getText().toString().length()<=6)
                    passwordEditText.setError("Password should atleast 6 character");
                else {
                    //Progress dialog
                    progressDialog=new ProgressDialog(RegisterActivity.this);
                    progressDialog.setTitle("Logging you inn...");
                    progressDialog.setMessage("Please wait while we are logging you inn..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    signup( displayName.getText().toString(),emailEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });
    }

    private void signup(final String displayname, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    addToDatabase(displayname);
                    UpdateUI();
                }
                else {
                    progressDialog.hide();
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToDatabase(final String displayname) {
        FirebaseUser fuser=mAuth.getCurrentUser();
        final String uid=fuser.getUid();
        System.out.println("uid");
        reference=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("uid",uid);
                hashMap.put("name",displayname);
                hashMap.put("status","Hey,there im using chat App!");
                hashMap.put("image","default");
                hashMap.put("thumbnil","default");
                reference.setValue(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void UpdateUI() {
        progressDialog.dismiss();
        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}