package com.example.catdogapp.dog.pojo;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("attributes")
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }
}
