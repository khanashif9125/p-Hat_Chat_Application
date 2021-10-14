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

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEditText,passwordEditText;
    Button login_btn;
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailEditText=findViewById(R.id.EmailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        login_btn=findViewById(R.id.reg_btn);

        toolbar=findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailEditText.getText().toString().length()==0||passwordEditText.getText().toString().length()==0)
                    passwordEditText.setError("Make sure you have entered something");
                else {
                    //Progress dialog
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Logging you inn...");
                    progressDialog.setMessage("Please wait while we are logging you inn..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    login(emailEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    UpdateUI();
                }
                else {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateUI() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}