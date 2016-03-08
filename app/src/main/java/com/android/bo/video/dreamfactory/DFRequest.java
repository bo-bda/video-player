package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Created by Bo on 07.03.2016.
 */
public class DFRequest<T> {
    @SerializedName("resource")
    @Expose
    private List<T> resource;

    public DFRequest(List<T> resource) {
        this.resource = resource;
    }
}
