package com.workforashif.basicchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button reg_btn,login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        reg_btn=findViewById(R.id.reg_button);
        login_btn=findViewById(R.id.reg_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}