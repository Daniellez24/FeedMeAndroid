package com.example.feedme.models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance() {
        return _instance;
    }

    private Model() {

    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public Object getCurrentUser() {
        return firebaseModel.mAuth.getCurrentUser();
    }

    // we return the data using the callback we get as argument
    public void signInWithEmailAndPassword(String email, String password, Listener<Task<AuthResult>> callback) {
        firebaseModel.signInWithEmailAndPassword(email, password, callback);
    }

    public void createUserWithEmailAndPassword(String email, String password, Listener<Task<AuthResult>> callback) {
        firebaseModel.createUserWithEmailAndPassword(email, password, callback);
    }

    public void signoutUser(Listener<Void> callback) {
        firebaseModel.signoutUser(callback);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventMyRecipesLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    private MutableLiveData<List<Recipe>> myRecipesList = new MutableLiveData<>();

    public LiveData<List<Recipe>> getMyRecipesList() {
        myRecipesList.setValue(
                localDb.recipeDao().getRecipeByUserId(
                        firebaseModel.getCurrentUserId()).getValue()
        );

        if (myRecipesList.getValue() == null) {
            refreshMyRecipesList();
        }
        return myRecipesList;
    }

    public void addRecipe(Recipe recipe, Listener<Void> listener) {
        firebaseModel.addRecipe(recipe, (Void) -> {
            //refresh all recipes
            listener.onComplete(null);
        });
    }

    public void refreshMyRecipesList() {
//        EventMyRecipesLoadingState.setValue(LoadingState.LOADING);
        String userId = firebaseModel.getCurrentUserId();
        firebaseModel.getRecipesByUserId(userId, new Listener() {
            @Override
            public void onComplete(Object data) {
                List<Recipe> usersRecipes = (List<Recipe>) data;
                executor.execute(() -> {
                    for (Recipe recipe : usersRecipes) {
                        //TODO save users recipe into local db -
                        localDb.recipeDao().insertAll(recipe);
                    }

                    myRecipesList.postValue(usersRecipes);
                });
            }
        });
//        Long localLastUpdate = Recipe.getLocalLastUpdate();
    }

    public String getCurrentUserId() {
        return firebaseModel.getCurrentUserId();
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }


    private MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

    public LiveData<List<Recipe>> getFeedItems() {
        recipeList.setValue(localDb.recipeDao().getAll().getValue());

        if (recipeList.getValue() == null) {
            refreshRecipes();
        }
        return recipeList;
    }


    public void refreshRecipes() {
        firebaseModel.getFeedItems(new Listener() {
            @Override
            public void onComplete(Object data) {
                List<Recipe> list = (List<Recipe>) data;
                executor.execute(() -> {
                    for (Recipe r : list) {
                        localDb.recipeDao().insertAll(r);
                    }
                    recipeList.postValue(list);
                });
            }
        });
    }

}
