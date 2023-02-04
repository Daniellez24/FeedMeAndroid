package com.example.feedme.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;

import java.util.List;

public class MyRecipesFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> data = Model.instance().getMyRecipesList();

    public LiveData<List<Recipe>> getData(){
        return data;
    }
}
