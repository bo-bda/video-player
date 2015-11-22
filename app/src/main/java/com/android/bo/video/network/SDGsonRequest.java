package com.android.bo.video.network;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SDGsonRequest<T> extends GsonRequest<T> {


    protected OnReLoginListener onReLoginListener;
    private Map<String, String> params;
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private boolean isRefreshToken = true;

    public SDGsonRequest(String url, Class<T> clazz, Map<String, String> headers, ApiListener<T> listener) {
        super(url, clazz, headers, new DefaultSuccessListener<>(listener), new DefaultErrorListener(listener));
        this.clazz = clazz;
        this.setShouldCache(false);
    }

    public SDGsonRequest(int type, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params, ApiListener<T> listener) {
        super(type, url, clazz, headers, params, new DefaultSuccessListener<>(listener), new DefaultErrorListener(listener));
        this.clazz = clazz;
        this.setShouldCache(false);
    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String e = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            ApiError apiError = null;
            try {
                apiError = this.gson.fromJson(e, ApiError.class);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (apiError != null && !TextUtils.isEmpty(apiError.getError())) {
                return Response.error(new VolleyError(apiError.getError()));
            } else if (String.class.equals(this.clazz))
                return Response.success((T) e, HttpHeaderParser.parseCacheHeaders(response));
            else
                return Response.success(this.gson.fromJson(e, this.clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        } catch (JsonSyntaxException var4) {
            return Response.error(new ParseError(var4));
        }
    }

    public void setOnReLoginListener(OnReLoginListener onReLoginListener) {
        this.onReLoginListener = onReLoginListener;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error) {
        boolean isRefreshError = false;
        if (error.networkResponse != null) {
            // isRefreshError = error.networkResponse.statusCode == ResponseCodes.AUTH_TOKEN_NEED_REGENERATE;
        }
        if (isRefreshError && isRefreshToken && onReLoginListener != null) {
            isRefreshToken = false;
            onReLoginListener.onReLoginListener(this);
        } else {
            super.deliverError(error);
        }
    }

    public interface OnReLoginListener {
        void onReLoginListener(SDGsonRequest request);
    }

    private static class DefaultSuccessListener<T> implements Response.Listener<T> {

        private ApiListener<T> networkListener;

        public DefaultSuccessListener(ApiListener<T> networkListener) {
                this.networkListener = networkListener;
        }

        @Override
        public void onResponse(T response) {
            if (networkListener != null)
                networkListener.deliverSuccess(response);
        }
    }

    private static class DefaultErrorListener implements Response.ErrorListener {

        private ApiListener networkListener;
        private final Gson gson = new Gson();

        private DefaultErrorListener(ApiListener networkListener) {
                this.networkListener = networkListener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            if (networkListener != null)
                networkListener.deliverError(handleVolleyError(error));
        }

        private ApiError handleVolleyError(VolleyError error) {
            ApiError serverErrorModel = null;
            try {
                String json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                serverErrorModel = gson.fromJson(json, ApiError.class);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            if (serverErrorModel == null) {
                serverErrorModel = new ApiError();
                serverErrorModel.setError(error.getMessage());
            }
            if (error instanceof TimeoutError) {
                serverErrorModel.setError(error.getMessage());
            }
            return serverErrorModel;
        }
    }
}
