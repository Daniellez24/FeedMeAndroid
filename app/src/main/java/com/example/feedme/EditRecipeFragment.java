package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feedme.databinding.FragmentEditRecipeBinding;
import com.example.feedme.models.Recipe;

public class EditRecipeFragment extends Fragment {

    FragmentEditRecipeBinding binding;
    Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditRecipeFragmentArgs args = EditRecipeFragmentArgs.fromBundle(getArguments());
        recipe = args.getRecipe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Log.d("EDIT", recipe.getRecipeTitle() + ", " + recipe.getRecipeBody());
        //TODO: show recipe details in the editRecipe page, and then change them in firestore and storage

        return view;
    }
}