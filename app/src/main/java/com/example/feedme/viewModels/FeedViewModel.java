package com.example.feedme.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.feedme.models.GenericCallback;
import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;
import java.util.List;



public class FeedViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipesList = Model.instance().getFeedItems();

    public LiveData<List<Recipe>> getRecipes() {
        return recipesList;
    }
}

