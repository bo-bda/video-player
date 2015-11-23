package com.android.bo.video.network;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.android.bo.video.BoApplication;
import com.android.bo.video.m3uparser.M3UParser;
import com.android.bo.video.models.Channels;
import com.android.bo.video.utils.Types;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ApiClient extends Client {

    private static final String API_THREAD_NAME = "API_THREAD";
    private static ApiClient mInstance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public ApiClient() {
        super(API_THREAD_NAME);
        requestQueue = Volley.newRequestQueue(BoApplication.getAppContext());
        imageLoader = new ImageLoader(requestQueue);
    }

    public static ApiClient getSharedInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        ArrayMap<String, String> headers = new ArrayMap<>();
        imageLoader.setHeaders(headers);
        return imageLoader;
    }

    private void addRequest(Request request, ApiListener listener) {
        if (hasConnection()) {
            requestQueue.add(request);
        } else {
            ApiError error = new ApiError();
            error.setError(NO_INTERNET_ERROR);
            listener.deliverError(error);
        }
    }

    public void getChannels(Types.Uris url, ApiListener<Channels> listener) {
        addRequest(new GetEventsRequest(url, listener.getType(), listener), listener);
    }

    private class GetEventsRequest extends SDGsonRequest<Channels> {

        private Types.Uris uri;

        public GetEventsRequest(Types.Uris uri, Class<Channels> clazz, ApiListener<Channels> listener) {
            super(uri.getUri(), clazz, null, listener);
            this.uri = uri;
        }

        @Override
        protected Response<Channels> parseNetworkResponse(NetworkResponse response) {
            Channels channels = new Channels();
            try {
                String string = new String(response.data, "UTF-8");
                if (!TextUtils.isEmpty(string)) {
                    M3UParser m3UParser = new M3UParser();
                    channels = m3UParser.parseFile(string, uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }
            return Response.success(channels, HttpHeaderParser.parseCacheHeaders(response));
        }
    }


}
