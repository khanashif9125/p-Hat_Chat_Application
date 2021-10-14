package com.workforashif.basicchatapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusDialog extends AppCompatDialogFragment {
    EditText statusEditText;
    String text=null;
    DatabaseReference reference;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.status_dialog,null);
        statusEditText=view.findViewById(R.id.statusText);
        builder.setView(view)
                .setTitle("New Status")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text=statusEditText.getText().toString();

                        if(text!=null){
                            String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            reference.child("status").setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(view.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(view.getContext(), "Something Went Wrong :(", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });


        return builder.create();
    }
}
