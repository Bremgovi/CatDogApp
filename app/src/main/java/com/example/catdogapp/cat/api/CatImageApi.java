package com.example.catdogapp.cat.api;

import com.example.catdogapp.cat.pojo.CatImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface CatImageApi {
    @GET("/v1/images/search")
    Call<List<CatImage>> getData();
}
