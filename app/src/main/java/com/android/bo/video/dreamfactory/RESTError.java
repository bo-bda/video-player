package com.android.bo.video.dreamfactory;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/*
 * Created by Bo on 06.03.2016.
 */
public class RESTError {

    private RESTError() {

    }

    public static DFApiError getInstance(Response response) {
        DFApiError error;
        try {
            Converter<ResponseBody, DFApiError> errorConverter = RESTClient.getInstance().getRetrofit().responseBodyConverter(DFApiError.class, new Annotation[0]);
            error = errorConverter.convert(response.errorBody());
        } catch (Exception e) {
            e.printStackTrace();
            return new DFApiError(new DFError("Unknown Server Error"));
        }
        return error;
    }

    @SerializedName("errors")
    private HashMap<String, Object> errors;

    public HashMap<String, Object> getErrors() {
        return errors;
    }

    public String getErrorsString() {
        StringBuilder db = new StringBuilder();
        if (errors != null) {
            for (Map.Entry<String, Object> entry : errors.entrySet()) {
                db.append(entry.getKey());
                db.append(":");
                db.append(entry.getValue());
                db.append(" ");
            }
            return db.toString();
        }
        return "Unknown server error";
    }

    public String getTextViewString() {
        StringBuilder db = new StringBuilder();
        String error = "Unknown server error";
        if (errors != null) {
            for (Map.Entry<String, Object> entry : errors.entrySet()) {
                db.append(entry.getValue());
            }
            error = db.toString();
            if (error.length() > 2) {
                error = error.substring(1, error.length() - 1);
            }

        }
        return error;
    }
}
