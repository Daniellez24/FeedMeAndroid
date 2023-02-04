package com.example.feedme.models;

public class Recipe {
    String recipeId, userId;
    String recipeImage;
    String recipeTitle, recipeBody;

    public Recipe(String recipeImage, String recipeTitle, String recipeBody) {
        this.recipeImage = recipeImage;
        this.recipeTitle = recipeTitle;
        this.recipeBody = recipeBody;
    }

    public static Long getLocalLastUpdate() {
        //TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return null;
    }

    public String getRecipeImage() {
        return recipeImage;
    }


    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeBody() {
        return recipeBody;
    }


}
