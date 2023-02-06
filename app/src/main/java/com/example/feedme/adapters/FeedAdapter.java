package com.example.feedme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.models.Recipe;
import com.example.feedme.models.Tip;
import com.example.feedme.viewHolders.RecipeViewHolder;

import java.util.LinkedList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private List<Recipe> recipes = new LinkedList<>();

    public void setData(List<Recipe> data) {
        this.recipes = data;
        notifyDataSetChanged();
    }

    public FeedAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if(recipes == null) return 0;
        return recipes.size();
    }
}
