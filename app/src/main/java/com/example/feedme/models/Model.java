package com.example.feedme.models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

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

    public String getCurrentUserId() {
        return firebaseModel.getCurrentUserId();
    }

    public void editUser(String name, String Image, Listener<Void> callback){
        firebaseModel.editUser(name, Image, callback);
    }

    public void getUserProfileData(Listener<User> listener){
        firebaseModel.getUserProfileData(listener);
    }

    public void editRecipe(Recipe recipe, Listener<Void> listener){
        firebaseModel.editRecipe(recipe, listener);
    }

    public void deleteRecipe(Recipe recipe, Listener<Void> listener){
        firebaseModel.deleteRecipe(recipe, listener);
    }

    public void addRecipe(Recipe recipe, Listener<Void> listener) {
        firebaseModel.addRecipe(recipe, listener);
    }

    public void getSelectedRecipeData(String recipeId ,Listener<Recipe> listener){
        firebaseModel.getSelectedRecipeData(recipeId, listener);
    }

    private MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

    private MutableLiveData<List<Recipe>> myRecipesList = new MutableLiveData<>();

    public LiveData<List<Recipe>> getMyRecipesList() {
        myRecipesList.setValue(
                localDb.recipeDao().getRecipeByUserId(firebaseModel.getCurrentUserId()).getValue()
        );
        if (myRecipesList == null || myRecipesList.getValue() == null) {
            refreshMyRecipesList();
        }
        return myRecipesList;
    }

    public LiveData<List<Recipe>> getFeedItems() {
        recipeList.setValue(localDb.recipeDao().getAll().getValue());

        if (recipeList.getValue() == null) {
            refreshRecipes();
        }
        return recipeList;
    }


    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventMyRecipesLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> EventFeedLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public void refreshRecipes() {
        EventFeedLoadingState.setValue(LoadingState.LOADING);
        firebaseModel.getFeedItems(new Listener() {
            @Override
            public void onComplete(Object data) {
                List<Recipe> list = (List<Recipe>) data;
                executor.execute(() -> {
                    for (Recipe r : list) {
                        localDb.recipeDao().insertAll(r);
                    }
                    recipeList.postValue(list);
                    EventFeedLoadingState.postValue(LoadingState.NOT_LOADING);
                });
            }
        });
    }

    public void refreshMyRecipesList() {
        EventMyRecipesLoadingState.setValue(LoadingState.LOADING);
        String userId = firebaseModel.getCurrentUserId();
        firebaseModel.getRecipesByUserId(userId, new Listener() {
            @Override
            public void onComplete(Object data) {
                List<Recipe> usersRecipes = (List<Recipe>) data;
                executor.execute(() -> {
                    localDb.beginTransaction();
                    try{
                        for (Recipe recipe : usersRecipes) {
                            localDb.recipeDao().insertAll(recipe);
                        }
                        myRecipesList.postValue(usersRecipes);
                        localDb.setTransactionSuccessful();
                    } finally {
                        localDb.endTransaction();
                    }
                    EventMyRecipesLoadingState.postValue(LoadingState.NOT_LOADING);
                });
            }
        });
    }

}
