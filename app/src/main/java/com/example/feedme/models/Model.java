package com.example.feedme.models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

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


    private LiveData<List<Recipe>> recipeList = null;

    public LiveData<List<Recipe>> getFeedItems(){

        recipeList = localDb.recipeDao().getAll();

        if (recipeList == null) {
            refreshRecipes();
        }
        return recipeList;
    }


    public void refreshRecipes(){
        firebaseModel.getFeedItems(new Listener() {
            @Override
            public void onComplete(Object data) {
                Log.d("danilelee", data + "");
                executor.execute(() -> {
//            localDb.recipeDao().insertAll(new Recipe("gA2lpMhGJTf8EMEFfaO5zxjwEZdK2","https://firebasestorage.googleapis.com/v0/b/feedme-android.appspot.com/o/images%2FSat%20Jan%2021%2015%3A21%3A47%20GMT%2B02%3A00%202023.jpg?alt=media&token=3761124f-e31b-4800-a481-fb48ac74c529","make pasta","sdasdas"));
//            localDb.recipeDao().insertAll(new Recipe("gA2lpMhGJTf8EMEFfaO5zxjwEZsdK2","https://firebasestorage.googleapis.com/v0/b/feedme-android.appspot.com/o/images%2FSat%20Jan%2021%2015%3A21%3A47%20GMT%2B02%3A00%202023.jpg?alt=media&token=3761124f-e31b-4800-a481-fb48ac74c529","make pasta","sdasdas"));
//            localDb.recipeDao().insertAll(new Recipe("gA2lpMhGJTf8EMEFfaO5zxjwEZsK2","https://firebasestorage.googleapis.com/v0/b/feedme-android.appspot.com/o/images%2FSat%20Jan%2021%2015%3A21%3A47%20GMT%2B02%3A00%202023.jpg?alt=media&token=3761124f-e31b-4800-a481-fb48ac74c529","make pasta","sdasdas"));
                });

            }
        });
    }

}
