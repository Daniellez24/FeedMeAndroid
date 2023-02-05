package com.example.feedme;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feedme.adapters.TipsRecyclerAdapter;
import com.example.feedme.databinding.FragmentTipsBinding;
import com.example.feedme.models.Tip;
import com.example.feedme.models.TipModel;
import com.example.feedme.viewModels.TipsFragmentViewModel;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TipsFragment extends Fragment {

    FragmentTipsBinding binding;
    TipsRecyclerAdapter adapter;
    TipsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTipsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.tipsFragmentRv.setHasFixedSize(true);
        binding.tipsFragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TipsRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.tipsFragmentRv.setAdapter(adapter);

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(TipsFragmentViewModel.class);
    }
}