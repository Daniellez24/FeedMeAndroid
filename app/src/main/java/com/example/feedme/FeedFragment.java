package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feedme.adapters.FeedAdapter;
import com.example.feedme.databinding.FragmentFeedBinding;
import com.example.feedme.models.FirebaseCallback;
import com.example.feedme.models.FirebaseModel;
import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;
import com.example.feedme.viewModels.FeedViewModel;

import java.util.List;


public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private RecyclerView feedRecyclerView;
    private FeedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        feedRecyclerView = binding.feedFragmentFeedsRv;
        feedRecyclerView.hasFixedSize();
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FeedViewModel feedViewModel = new FeedViewModel();
        adapter = new FeedAdapter((List<Recipe>) feedViewModel.getRecipes());
        feedRecyclerView.setAdapter(adapter);

        Model.instance().getFeedItems(new FirebaseCallback() {
            @Override
            public void onCallback(List<Recipe> list) {
                //TODO add spinner to this class + remove it after setting the data
                feedViewModel.setData(list);
                adapter.notifyDataSetChanged();
            }
        });

//        feedViewModel.getRecipes().observe(getViewLifecycleOwner(), list -> {
//            adapter.setData(list);
//        });

        return view;
    }
}



