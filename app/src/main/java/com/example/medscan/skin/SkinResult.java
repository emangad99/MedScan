package com.example.medscan.skin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkinResult {

    @SerializedName("data")
    @Expose
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
