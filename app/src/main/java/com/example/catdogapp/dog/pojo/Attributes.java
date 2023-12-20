package com.example.catdogapp.dog.pojo;

import com.google.gson.annotations.SerializedName;

public class Attributes {
    @SerializedName("body")
    private String body;

    public String getBody() {
        return body;
    }
}