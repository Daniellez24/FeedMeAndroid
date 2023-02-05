package com.example.feedme.models;

import java.util.List;

public interface FirebaseCallback {
    void onCallback(List<Recipe> list);
}
