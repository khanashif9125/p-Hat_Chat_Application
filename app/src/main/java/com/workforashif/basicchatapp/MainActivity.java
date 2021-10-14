package com.workforashif.basicchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    SetionPageAdapter setionPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.main_page_toolbar);

        tabLayout=findViewById(R.id.tablaout);
        viewPager=findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat App!");

        setionPageAdapter=new SetionPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(setionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
            updateUI();
    }

    private void updateUI() {
        Intent intent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.All_Users:
                AllUsers();
                return true;
            case R.id.Account_settings:
                AccountsettingUI();
                return true;
            case R.id.logout:
                mAuth.getInstance().signOut();
                updateUI();
                return true;
        }
        return false;
    }

    private void AccountsettingUI() {
        Intent intent=new Intent(MainActivity.this,AccountSetting.class);
        startActivity(intent);
    }
    private void AllUsers() {
        Intent intent = new Intent(MainActivity.this, All_UsersActivity.class);
        startActivity(intent);
    }
}