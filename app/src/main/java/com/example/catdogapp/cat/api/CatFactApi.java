package com.example.catdogapp.cat.api;

import com.example.catdogapp.cat.pojo.CatFact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactApi {
    @GET("fact") //facts/random
    Call<CatFact> getCatFact();
}
