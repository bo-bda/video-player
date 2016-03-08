package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Bo on 06.03.2016.
 */
public class DFError {

    @SerializedName("context")
    @Expose
    private Object context;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("trace")
    @Expose
    private List<String> trace = new ArrayList<>();

    public DFError(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The context
     */
    public Object getContext() {
        return context;
    }

    /**
     *
     * @param context
     * The context
     */
    public void setContext(Object context) {
        this.context = context;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The trace
     */
    public List<String> getTrace() {
        return trace;
    }

    /**
     *
     * @param trace
     * The trace
     */
    public void setTrace(List<String> trace) {
        this.trace = trace;
    }
}
