package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Bo on 06.03.2016.
 */
public class DFApiError {

    @SerializedName("error")
    @Expose
    private DFError error;

    public DFApiError(DFError error) {
        this.error = error;
    }

    /**
     * @return The error
     */
    public DFError getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(DFError error) {
        this.error = error;
    }
}
