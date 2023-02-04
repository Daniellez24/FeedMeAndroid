package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedme.adapters.MyRecipesRecyclerAdapter;
import com.example.feedme.viewModels.MyRecipesFragmentViewModel;

public class UserRecipesFragment extends Fragment {
    //TODO: on edit, save edited recipe in the db
//    private RecyclerView myRecpiesList;

    //FragmentMyRecipesListBinding binding;
    MyRecipesRecyclerAdapter adapter;
    MyRecipesFragmentViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_recipes, container, false);

        myRecpiesList = view.findViewById(R.id.userRecpieFragment_recipes_rv);

        return view;
    }

}