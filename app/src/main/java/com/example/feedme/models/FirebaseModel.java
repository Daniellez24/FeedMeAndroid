package com.example.feedme.models;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.feedme.LoginActivity;
import com.example.feedme.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                callback.onComplete(task);
            }
        });

    }

    public void createUserWithEmailAndPassword(String email, String password, Model.Listener<Task<AuthResult>> callback){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.onComplete(task);
                addNewUser();
            }
        });

    }

    public void signoutUser(Model.Listener<Void> callback){
        mAuth.signOut();
        callback.onComplete(null);
    }

    // add user to the firestore db (with default data)
    public void addNewUser(){
        String userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> json = new HashMap<>();
        json.put("id", userId);
        json.put("name", "name");
        json.put("image", "");
        json.put("recipes", new ArrayList<>());

        db.collection("users").document(userId).set(json).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("TAG", "success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "failure");
            }
        });
    }



}
