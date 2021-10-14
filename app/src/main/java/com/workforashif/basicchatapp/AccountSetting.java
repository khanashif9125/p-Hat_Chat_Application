package com.workforashif.basicchatapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetting extends AppCompatActivity {
    CircleImageView imageView;
    TextView username,status;
    Button chng_status_btn,chng_image_btn;
    Toolbar toolbar;
    FirebaseAuth mAuth;

    DatabaseReference reference;
    FirebaseUser fuser;
    StorageReference Sreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        imageView=findViewById(R.id.profile_imgview);
        username=findViewById(R.id.UsernameTextView);
        status=findViewById(R.id.statusTextView);
        chng_image_btn=findViewById(R.id.chng_img_button);
        chng_status_btn=findViewById(R.id.chng_status_button);
        toolbar=findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Settings ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chng_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus();
            }
        });

        chng_image_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                else
                    getPhoto();

            }
        });

        mAuth=FirebaseAuth.getInstance();
        fuser=mAuth.getCurrentUser();
        String uid=fuser.getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("name").getValue().toString();
                String statuss=snapshot.child("status").getValue().toString();
                username.setText(name);
                status.setText(statuss);
                loadImage(snapshot.child("image").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountSetting.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Sreference = FirebaseStorage.getInstance().getReference().child("profile_image").child(mAuth.getUid()+".jpg");

    }
    private void getPhoto(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
    }

    private void setStatus() {
        StatusDialog statusDialog=new StatusDialog();
        statusDialog.show(getSupportFragmentManager(),"Status Dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            Uri uri=data.getData();

            CropImage.activity(uri).setAspectRatio(1,1).start(this);

        }
        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult activityResult=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                final ProgressDialog dialog=new ProgressDialog(this);
                dialog.setTitle("Uploading ...");
                dialog.setMessage("We are working on it :)");
                dialog.show();

                Uri uri= activityResult.getUri();

                Sreference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AccountSetting.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Sreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url =uri.toString();
                                    saveImage(url);
                                    loadImage(url);
                                }
                            });
//                            loadImage(snapshot.child("image").getValue().toString());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(AccountSetting.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    }
                });
            }
            else
                Toast.makeText(this, "Something Went Wrong :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(final String url) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map hashMap=new HashMap<>();

                hashMap.put("image",url);
                reference.updateChildren(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                getPhoto();
        }
    }

    private void loadImage(String image){
        Picasso.with(getApplicationContext()).load(image).into(imageView);
    }
}