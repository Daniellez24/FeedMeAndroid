package com.example.feedme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.models.Recipe;
import com.example.feedme.viewHolders.MyRecipeViewHolder;

import java.util.List;

public class MyRecipesRecyclerAdapter extends RecyclerView.Adapter<MyRecipeViewHolder> {

    LayoutInflater inflater;
    List<Recipe> data;
    OnItemClickListener listener;

    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(List<Recipe> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public MyRecipesRecyclerAdapter(LayoutInflater inflater, List<Recipe> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_item, parent, false);
        return new MyRecipeViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        holder.bind(recipe, position);
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        return data.size();
    }
}
