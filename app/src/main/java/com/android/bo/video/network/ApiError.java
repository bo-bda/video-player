package com.android.bo.video.network;

import com.google.gson.annotations.SerializedName;

public class ApiError {


    @SerializedName("err")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}