package com.example.feedme.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.feedme.models.Recipe;

import java.util.LinkedList;
import java.util.List;



public class FeedViewModel extends ViewModel {
    private List<Recipe> recipesList = new LinkedList<>();

    public List<Recipe> getRecipes() {
        return recipesList;
    }

    public void setData (List<Recipe> list){
        recipesList = list;
    }


}

