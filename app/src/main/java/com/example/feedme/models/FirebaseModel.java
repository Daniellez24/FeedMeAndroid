package com.example.feedme.models;

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
                callback.onComplete(task);
            }
        });

    }

    //TODO: when user is sign up, add it to the users collection (id, name, image, recipe array)
    public void createUserWithEmailAndPassword(String email, String password, Model.Listener<Task<AuthResult>> callback){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.onComplete(task);
            }
        });
    }

    public void signoutUser(Model.Listener<Void> callback){
        mAuth.signOut();
        callback.onComplete(null);
    }



}
