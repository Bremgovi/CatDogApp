package com.example.catdogapp.dog.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DogFact {
    @SerializedName("data")
    @Expose
    private Data[] data;
    public Data[] getData() {
        return data;
    }
}

