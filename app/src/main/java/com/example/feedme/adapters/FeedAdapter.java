package com.example.feedme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.models.FeedModel;
import com.example.feedme.viewHolders.FeedViewHolder;

import java.util.List;

class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private List<FeedModel> feeds;

    public FeedAdapter(List<FeedModel> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedModel feed = feeds.get(position);
//        FeedViewHolder.feedTitle.setText(feed.getTitle());
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }
}
