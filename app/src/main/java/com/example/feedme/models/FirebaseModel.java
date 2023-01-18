package com.example.feedme.models;

import static androidx.core.content.ContextCompat.Api16Impl.startActivity;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.feedme.LoginActivity;
import com.example.feedme.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseModel {

    FirebaseFirestore db;
    FirebaseAuth mAuth;


    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // set the firestore to *not* store in local db
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void signInWithEmailAndPassword(String email, String password, Model.Listener<Task<AuthResult>> callback){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.class, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.class, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.class, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
