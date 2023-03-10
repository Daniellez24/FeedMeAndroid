package com.example.feedme.models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.feedme.MyApplication;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Recipe implements Serializable {
    // recipe id auto-generated by firebase
    @PrimaryKey
    @NonNull
    String userId = "";
    String recipeImage = "";
    String recipeTitle = "";
    String recipeBody = "";
    String recipeId = "";

    public Long lastUpdated;

    static final String TITLE = "title";
    static final String USERID = "userId";
    static final String IMAGE = "image";
    static final String BODY = "body";
    static final String RECIPEID = "recipeId";
    static final String COLLECTION = "recipes";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "recipes_local_last_updated";


//    public Recipe(String userId, String recipeImage, String recipeTitle, String recipeBody) {
//        this.userId = userId;
//        this.recipeImage = recipeImage;
//        this.recipeTitle = recipeTitle;
//        this.recipeBody = recipeBody;
//    }

    public Recipe(@NonNull String userId, String recipeImage, String recipeTitle, String recipeBody, String recipeId) {
        this.userId = userId;
        this.recipeImage = recipeImage;
        this.recipeTitle = recipeTitle;
        this.recipeBody = recipeBody;
        this.recipeId = recipeId;
    }

    public Recipe(){

    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getUserId(){ return userId; }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeBody() {
        return recipeBody;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public void setRecipeBody(String recipeBody) {
        this.recipeBody = recipeBody;
    }

    public static Recipe fromJson(Map<String, Object> json){
        String userId = (String)json.get(USERID);
        String title = (String) json.get(TITLE);
        String body = (String) json.get(BODY);
        String image = (String) json.get(IMAGE);
        String recipeId = (String) json.get(RECIPEID);

        Recipe recipe = new Recipe(userId,image, title, body, recipeId);

        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            recipe.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }

        return recipe;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();

        json.put(USERID, getUserId());
        json.put(TITLE, getRecipeTitle());
        json.put(BODY, getRecipeBody());
        json.put(IMAGE, getRecipeImage());
        json.put(RECIPEID, getRecipeId());

        return json;
    }


    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
