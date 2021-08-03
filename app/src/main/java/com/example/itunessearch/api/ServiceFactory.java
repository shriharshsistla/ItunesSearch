package com.example.itunessearch.api;

import  retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    public static APIservice getInstance() {

        String baseUrl = "https://itunes.apple.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIservice.class);
    }
}
