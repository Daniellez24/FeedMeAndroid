package com.example.feedme.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseModel {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;


    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

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

    public String getCurrentUserId(){
        return mAuth.getCurrentUser().getUid();
    }

    void addRecipe(Recipe recipe, Model.Listener<Void> listener){
        db.collection("recipes").add(recipe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "recipe added to firestore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error adding recipe to firestore");
            }
        });
    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + name + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

}
