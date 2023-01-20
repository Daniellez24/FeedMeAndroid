package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserRecipesFragment extends Fragment {
    private RecyclerView allRecpies;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_recipes, container, false);
        
        allRecpies = view.findViewById(R.id.userRecpieFragment_recipes_rv);
        title = view.findViewById(R.id.userRecpieFragment_title_tv);

        return view;
    }

}