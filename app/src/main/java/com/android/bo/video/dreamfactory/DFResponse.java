package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Bo on 07.03.2016.
 */
public class DFResponse<T> {
    @SerializedName("resource")
    @Expose
    private ArrayList<T> resource = new ArrayList<>();

    /**
     *
     * @return
     * The resource
     */
    public ArrayList<T> getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     * The resource
     */
    public void setResource(ArrayList<T> resource) {
        this.resource = resource;
    }
}
