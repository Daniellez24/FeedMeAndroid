package com.example.feedme;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedme.adapters.MyRecipesRecyclerAdapter;
import com.example.feedme.databinding.FragmentUserRecipesBinding;
import com.example.feedme.viewModels.MyRecipesFragmentViewModel;

public class UserRecipesFragment extends Fragment {
    //TODO: on edit, save edited recipe in the db
//    private RecyclerView myRecpiesList;

    FragmentUserRecipesBinding binding;
    MyRecipesRecyclerAdapter adapter;
    MyRecipesFragmentViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_user_recipes, container, false);
        binding = FragmentUserRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        myRecpiesList = view.findViewById(R.id.userRecpieFragment_recipes_rv);
        binding.userRecpieFragmentRecipesRv.setHasFixedSize(true);
        binding.userRecpieFragmentRecipesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecipesRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.userRecpieFragmentRecipesRv.setAdapter(adapter);

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        //TODO: swipe refresh?

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyRecipesFragmentViewModel.class);
    }
}