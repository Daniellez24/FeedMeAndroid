package com.example.feedme.viewModels;

import androidx.lifecycle.LiveData;

import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;

import java.util.List;

public class MyRecipesFragmentViewModel {
    private LiveData<List<Recipe>> data = Model.instance().getMyRecipesList();

    LiveData<List<Recipe>> getData(){
        return data;
    }
}
