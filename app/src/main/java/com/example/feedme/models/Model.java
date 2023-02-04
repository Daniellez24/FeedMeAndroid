package com.example.feedme.models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.feedme.LoginActivity;
import com.example.feedme.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    private FirebaseModel firebaseModel = new FirebaseModel();

    public static Model instance(){
        return _instance;
    }

    private Model(){

    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public Object getCurrentUser(){
        return firebaseModel.mAuth.getCurrentUser();
    }

    // we return the data using the callback we get as argument
    public void signInWithEmailAndPassword(String email,String password, Listener<Task<AuthResult>> callback){
        firebaseModel.signInWithEmailAndPassword(email,password, callback);
    }

    public void createUserWithEmailAndPassword(String email,String password,Listener<Task<AuthResult>> callback){
        firebaseModel.createUserWithEmailAndPassword(email, password, callback);
    }

    public void signoutUser(Listener<Void> callback){
        firebaseModel.signoutUser(callback);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }


    final public MutableLiveData<LoadingState> EventMyRecipesLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    private LiveData<List<Recipe>> myRecipesList;

    public LiveData<List<Recipe>> getMyRecipesList(){
        if(myRecipesList == null){
            //TODO: add local db
            //myRecipesList = localDB.recipeDAO().getAll();
            //refreshMyRecipes();
        }
        return myRecipesList;
    }

    public void addRecipe(Recipe recipe, Listener<Void> listener){
        firebaseModel.addRecipe(recipe, (Void) -> {
            //refresh all recipes
            listener.onComplete(null);
        });
    }

    public void refreshMyRecipesList(){
        EventMyRecipesLoadingState.setValue(LoadingState.LOADING);
        // TODO: implement getLocalLastUpdate() in Recipe
        Long localLastUpdate = Recipe.getLocalLastUpdate();
    }

    public String getCurrentUserId(){
        return firebaseModel.getCurrentUserId();
    }


    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener){
        firebaseModel.uploadImage(name, bitmap, listener);
    }
}
