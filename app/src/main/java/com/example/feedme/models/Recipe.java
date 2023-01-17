package com.example.feedme.models;

public class Recipe {
    int recipeImage, profilePic;
    String recipeTitle, recipeBody;

    public Recipe(int recipeImage, int profilePics, String recipeTitle, String recipeBody) {
        this.recipeImage = recipeImage;
        this.profilePic = profilePics;
        this.recipeTitle = recipeTitle;
        this.recipeBody = recipeBody;
    }

    public int getRecipeImage() {
        return recipeImage;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeBody() {
        return recipeBody;
    }


}
