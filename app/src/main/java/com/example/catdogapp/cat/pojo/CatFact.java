package com.example.catdogapp.cat.pojo;

import com.google.gson.annotations.SerializedName;

public class CatFact {
    @SerializedName("fact") //text
    private String text;
    public String getText() {
        return text;
    }
}
