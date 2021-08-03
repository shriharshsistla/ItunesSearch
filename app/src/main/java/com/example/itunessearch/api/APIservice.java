package com.example.itunessearch.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIservice {

    @GET("search")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TrackModel> getTracks(@Query("term") String term);
}
