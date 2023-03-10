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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseModel {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;


    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        // set the firestore to *not* store in local db
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }


    public void signInWithEmailAndPassword(String email, String password, Model.Listener<Task<AuthResult>> callback) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.onComplete(task);
            }
        });

    }

    public void createUserWithEmailAndPassword(String email, String password, Model.Listener<Task<AuthResult>> callback) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.onComplete(task);
                addNewUser();
            }
        });

    }

    public String getCurrentUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    public void signoutUser(Model.Listener<Void> callback) {
        mAuth.signOut();
        callback.onComplete(null);
    }

    // add user to the firestore db (with default data)
    public void addNewUser() {
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

    public void editUser(String name, String image, Model.Listener<Void> callback){
        String userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> json = new HashMap<>();
        json.put("id", userId);
        if(name != null)
            json.put("name", name);
        if(image != null)
            json.put("image", image);

        // update new fields
        db.collection("users").document(userId).update(json).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onComplete(null);
            }
        });

    }

    public void getFeedItems(Model.Listener callback) {
        final List<Recipe> recipes = new ArrayList<>();

        CollectionReference collectionReference = db.collection("recipes");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", "Test for return => " + document.getData());
                        Map<String, Object> data = document.getData();
                        String userId = (String) document.get("userId");
                        String recipeTitle = (String) document.get("recipeTitle");
                        String recipeBody = (String) document.get("recipeBody");
                        String recipeImage = (String) document.get("recipeImage");
                        String recipeId = (String) document.get("recipeId");

                        recipes.add(new Recipe(userId, recipeImage, recipeTitle, recipeBody, recipeId));
                    }
                    callback.onComplete(recipes);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
    }


    public void getRecipesByUserId(String id, Model.Listener callback) {
        final List<Recipe> myRecipes = new ArrayList<>();

        CollectionReference collectionReference = db.collection(Recipe.COLLECTION);
        collectionReference.whereEqualTo("userId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        String userId = (String) document.get("userId");
                        String recipeTitle = (String) document.get("recipeTitle");
                        String recipeBody = (String) document.get("recipeBody");
                        String recipeImage = (String) document.get("recipeImage");
                        String recipeId = (String) document.get("recipeId");

                        myRecipes.add(new Recipe(userId, recipeImage, recipeTitle, recipeBody, recipeId));
                    }
                    callback.onComplete(myRecipes);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
    }


    public void editRecipe(Recipe recipe, Model.Listener<Void> listener){
        Map<String, Object> json = new HashMap<>();
        json.put("userId", recipe.getUserId());
        json.put("recipeTitle", recipe.getRecipeTitle());
        json.put("recipeImage", recipe.getRecipeImage());
        json.put("recipeId", recipe.getRecipeId());
        json.put("recipeBody", recipe.getRecipeBody());

        db.collection("recipes").document(recipe.getRecipeId()).update(json).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onComplete(null);
            }
        });
    }


    public void deleteRecipe(Recipe recipe, Model.Listener<Void> listener){
        db.collection("recipes")
                .document(recipe.getRecipeId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(null);
                    }
                });
    }


    public void getUserProfileData(Model.Listener<User> listener){
        db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if(user != null){
                            listener.onComplete(user);
                        }
                    }
                });
    }

    public void getSelectedRecipeData(String recipeId ,Model.Listener<Recipe> listener){
        db.collection("recipes")
                .document(recipeId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Recipe recipe = documentSnapshot.toObject(Recipe.class);
                        if(recipe != null){
                            listener.onComplete(recipe);
                        }
                    }
                });
    }



    void addRecipe(Recipe recipe, Model.Listener<Void> listener) {
        db.collection("recipes").add(recipe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "recipe added to firestore");
                recipe.setRecipeId(documentReference.getId());
                documentReference.update("recipeId", documentReference.getId());
                listener.onComplete(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error adding recipe to firestore");
            }
        });
    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
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
                        if(listener != null)
                            listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

}
