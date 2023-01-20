package com.example.feedme.models;

public class Recipe {
    String recipeId, userId;
    int recipeImage;
    String recipeTitle, recipeBody;

    public Recipe(int recipeImage, String recipeTitle, String recipeBody) {
        this.recipeImage = recipeImage;
        this.recipeTitle = recipeTitle;
        this.recipeBody = recipeBody;
    }

    public int getRecipeImage() {
        return recipeImage;
    }


    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeBody() {
        return recipeBody;
    }


}
