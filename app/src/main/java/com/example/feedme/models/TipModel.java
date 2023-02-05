package com.example.feedme.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TipModel {
    final public static TipModel instance = new TipModel();

    final String BASE_URL = "https://tasty.p.rapidapi.com/";
    Retrofit retrofit;
    TipApi tipApi;

    private TipModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tipApi = retrofit.create(TipApi.class);
    }

    public LiveData<List<Tip>> getTipById(int id){
        MutableLiveData<List<Tip>> data = new MutableLiveData<>();

        Call<TipSearchResult> call = tipApi.getTipById(id);
        call.enqueue(new Callback<TipSearchResult>() {
            @Override
            public void onResponse(Call<TipSearchResult> call, Response<TipSearchResult> response) {
                if(response.isSuccessful()){
                    TipSearchResult res = response.body();

                    data.setValue(res.getList());
                } else {
                    Log.d("TAG","---------- getTipById response error");

                }
            }

            @Override
            public void onFailure(Call<TipSearchResult> call, Throwable t) {
                Log.d("TAG","---------- getTipById failed");
            }
        });

        return data;
    }

}
