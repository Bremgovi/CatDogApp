package com.example.catdogapp.dog.api;

import com.example.catdogapp.dog.pojo.DogImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogImageApi {
    @GET("/v1/images/search")
    Call<List<DogImage>> getData();
}
