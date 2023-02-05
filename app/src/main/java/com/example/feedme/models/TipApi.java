package com.example.feedme.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TipApi {
    @Headers({
            "X-RapidAPI-Key: 66d2bb7747mshf5d007d74c70c25p1740ccjsn957e724b96fd",
            "X-RapidAPI-Host: tasty.p.rapidapi.com"
    })
    @GET("tips/list")
    Call<TipSearchResult> getTipById(@Query("id") int id);

}
