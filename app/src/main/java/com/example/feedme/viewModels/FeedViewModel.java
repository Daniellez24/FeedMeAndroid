package com.example.feedme.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.feedme.models.Recipe;

import java.util.LinkedList;
import java.util.List;

public class FeedViewModel extends ViewModel {
    private List<Recipe> recipesList = new LinkedList<>();

    public List<Recipe> getRecipes() {
        return recipesList;
    }


    public void addRecipe(Recipe recipe){
        recipesList.add(recipe);
    }



    public void setData (List<Recipe> list){
        recipesList = list;
    }


}
