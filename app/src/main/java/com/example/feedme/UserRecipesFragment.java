package com.example.feedme;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedme.adapters.MyRecipesRecyclerAdapter;
import com.example.feedme.databinding.FragmentUserRecipesBinding;
import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;
import com.example.feedme.viewModels.MyRecipesFragmentViewModel;

public class UserRecipesFragment extends Fragment {

    FragmentUserRecipesBinding binding;
    MyRecipesRecyclerAdapter adapter;
    MyRecipesFragmentViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.userRecpieFragmentRecipesRv.setHasFixedSize(true);
        binding.userRecpieFragmentRecipesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecipesRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.userRecpieFragmentRecipesRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyRecipesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Recipe recipe = viewModel.getData().getValue().get(pos);
                UserRecipesFragmentDirections.ActionUserRecipesFragmentToEditRecipeFragment action = UserRecipesFragmentDirections.actionUserRecipesFragmentToEditRecipeFragment(recipe);
                Navigation.findNavController(view).navigate(action);
            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        Model.instance().EventMyRecipesLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            Model.instance().refreshMyRecipesList();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyRecipesFragmentViewModel.class);
    }
}