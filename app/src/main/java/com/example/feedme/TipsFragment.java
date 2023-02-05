package com.example.feedme;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feedme.models.Tip;
import com.example.feedme.models.TipModel;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TipsFragment extends Fragment {
    private RecyclerView factsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        factsRecyclerView = view.findViewById(R.id.factsFragment_all_facts_rv);

        LiveData<List<Tip>> data = TipModel.instance.getTipById(3550);
        data.observe(getViewLifecycleOwner(), list -> {
            list.forEach(item -> {
                Log.d("TAG", "tip: " + item.getTip_body());
            });
        });

        return view;
    }
}