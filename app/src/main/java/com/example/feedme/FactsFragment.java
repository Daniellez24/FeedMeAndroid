package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FactsFragment extends Fragment {
    private RecyclerView factsRecyclerView;
    private TextView factsTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        factsRecyclerView = view.findViewById(R.id.factsFragment_all_facts_rv);
        factsTitle = view.findViewById(R.id.factsFragment_title_tv);

        return view;
    }
}