package com.example.feedme.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TipSearchResult {
    @SerializedName("results")
    List<Tip> list;

    public List<Tip> getList() {
        return list;
    }

    public void setList(List<Tip> list) {
        this.list = list;
    }
}
