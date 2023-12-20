package com.example.catdogapp.dog.api;

import com.example.catdogapp.dog.pojo.DogFact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogFactApi {
    @GET("api/v2/facts")
    Call<DogFact> getDogFact();
}
