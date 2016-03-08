package com.android.bo.video.network;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.android.bo.video.BoApplication;
import com.android.bo.video.dreamfactory.DFUser;
import com.android.bo.video.m3uparser.M3UParser;
import com.android.bo.video.models.Channels;
import com.android.bo.video.utils.Types;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiClient extends Client {

    private static final String API_THREAD_NAME = "API_THREAD";
    private static ApiClient mInstance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static final String APP_KEY_DF = "37f49f20bea34d0b7b0564f49e78b9eb0d388bcc74c4c37d716aa030b2db523d";

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

    /*dreamfactory*/
    /*
    curl -X POST 'https://df-video-online.enterprise.dreamfactory.com/api/v2/db/_table/channels'
     --header 'Content-Type: application/json'
     --header 'Accept: application/json'
     --header 'X-DreamFactory-Api-Key: 36fda24fe5588fa4285ac6c6c2fdfbdb6b6bc9834699774c9bf777f706d05a88'
     --header 'X-DreamFactory-Session-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsInVzZXJfaWQiOjEsImVtYWlsIjoiYm9vLmJkYTg4QGdtYWlsLmNvbSIsImZvcmV2ZXIiOmZhbHNlLCJpc3MiOiJodHRwczpcL1wvZGYtdmlkZW8tb25saW5lLmVudGVycHJpc2UuZHJlYW1mYWN0b3J5LmNvbVwvYXBpXC92Mlwvc3lzdGVtXC9hZG1pblwvc2Vzc2lvbiIsImlhdCI6MTQ1NzIxNjM0OSwiZXhwIjoxNDU3MjE5OTQ5LCJuYmYiOjE0NTcyMTYzNDksImp0aSI6IjQ4YTdhMzcxZGMyYzllNTE3NDdkZDg4ZWIxNDIwZTBiIn0.O5bC5ik5tnvi4qJvSHwUjBFz89MAgrQfa42P4Y_nomo'
     --header 'Authorization: Basic Ym9vLmJkYTg4QGdtYWlsLmNvbToyMkJveWtvODg='
      -d '{
          "resource": [
            {
              "channel_name": "test",
              "country_name": "urkaine"
            }
          ]
        }
    */

    //login
    /*
    curl -X POST 'https://df-video-online.enterprise.dreamfactory.com/api/v2/user/session'
    --header 'Content-Type: application/json'
    --header 'Accept: application/json'
    --header 'X-DreamFactory-Api-Key: 36fda24fe5588fa4285ac6c6c2fdfbdb6b6bc9834699774c9bf777f706d05a88'
    --header 'X-DreamFactory-Session-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsInVzZXJfaWQiOjEsImVtYWlsIjoiYm9vLmJkYTg4QGdtYWlsLmNvbSIsImZvcmV2ZXIiOmZhbHNlLCJpc3MiOiJodHRwczpcL1wvZGYtdmlkZW8tb25saW5lLmVudGVycHJpc2UuZHJlYW1mYWN0b3J5LmNvbVwvYXBpXC92Mlwvc3lzdGVtXC9hZG1pblwvc2Vzc2lvbiIsImlhdCI6MTQ1NzIxNjM0OSwiZXhwIjoxNDU3MjI1NTI4LCJuYmYiOjE0NTcyMjE5MjgsImp0aSI6IjVmODliOTdkMTUxOGM1OWQ0YjNjNGRkNDA0MjIwMTEzIn0.7Pz1DFOIFU4NWTapOceiWQZXz9WE3Cpk8UuaA3RWmj4'
    --header 'Authorization: Basic Ym9vLmJkYTg4QGdtYWlsLmNvbToyMkJveWtvODg='
     -d '{
            "email": "boyko_dimka@mail.ru",
            "password": "123123123Aa",
            "duration": 0
        }'
    */
}
