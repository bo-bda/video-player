package com.android.bo.video.dreamfactory;

import android.util.Log;

import com.android.bo.video.models.Channel;
import com.android.bo.video.models.Channels;
import com.android.bo.video.utils.Storage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/*
 * Created by Bo on 06.03.2016.
 */
public class RESTClient {

    private static final String BASE_URL = "https://df-video-online.enterprise.dreamfactory.com/api/v2/";
    private static RESTClient ourInstance;
    private Retrofit retrofit;
    private static final String APP_KEY_DF = "37f49f20bea34d0b7b0564f49e78b9eb0d388bcc74c4c37d716aa030b2db523d";

    public static RESTClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new RESTClient();
        }
        return ourInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private RESTClient() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void login(Callback<DFUser> listener) {
        retrofit.create(DFService.class).login(DFCredantials.getInstance()).enqueue(listener);
    }

    public void getChannels(String token, Callback<DFResponse<DFChannel>> listener) {
        retrofit.create(DFService.class).getChannels(token).enqueue(listener);
    }


    public void sync(Channels<Channel> channels) {
        if (channels != null) {
            final String token = Storage.getInstance().getUserProfile().getSessionToken();
            for (final Channel channel : channels) {
                final DFChannel dfChannel = new DFChannel(channel.getName(), "unknown");
                ArrayList<DFUrlByChannelId> dfUrls = new ArrayList<>();
                for (String url : channel.getUri()) {
                    dfUrls.add(new DFUrlByChannelId(url));
                }
                dfChannel.setUrlByChannelId(dfUrls);
                DFRequest<DFChannel> channels1 = new DFRequest<>(Collections.singletonList(dfChannel));
                retrofit.create(DFService.class).createChannel(token, channels1)
                        .enqueue(new Callback<DFResponse<DFChannel>>() {
                            @Override
                            public void onResponse(Call<DFResponse<DFChannel>> call, Response<DFResponse<DFChannel>> response) {
                                if (response.isSuccess()) {
                                    Log.e("MyTag Sync", "sync onResponse:" + dfChannel.getChannelName());

//                                    createUrls(response, channel, token);
                                } else {
                                    Log.e("MyTag Sync", "sync onResponse error:" + dfChannel.getChannelName() +
                                            " " + RESTError.getInstance(response).getError().getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<DFResponse<DFChannel>> call, Throwable t) {
                                Log.e("MyTag Sync", "sync onFailure error:" + t.getLocalizedMessage());
                            }
                        });
            }
        }
    }

    /*private void createUrls(Response<DFChannelResponse<DFChannel>> response, Channel channel, String token) {
        DFChannelResponse<DFChannel> dfChannelResponse = response.body();
        ArrayList<DFUrl> dfUrls = new ArrayList<>();
        for (String url : channel.getUri()) {
            dfUrls.add(new DFUrl(url, dfChannelResponse.getResource().get(0).getId()));
        }
        retrofit.create(DFService.class).createUrl(token, dfUrls).enqueue(new Callback<DFChannelResponse>() {
            @Override
            public void onResponse(Call<DFChannelResponse> call, Response<DFChannelResponse> response) {

                if (response.isSuccess()) {
                    Log.e("MyTag Sync", "createUrls onResponse body:" + response.body().toString());

                } else {
                    Log.e("MyTag Sync", "createUrls error:" + RESTError.getInstance(response).getError().getMessage());
                }

            }

            @Override
            public void onFailure(Call<DFChannelResponse> call, Throwable t) {
                Log.e("MyTag Sync", "createUrls onFailure error:" + t.getLocalizedMessage());
            }
        });
    }*/

    public interface DFService {

        @POST("user/session")
        @Headers({"X-DreamFactory-Api-Key: " + APP_KEY_DF,
                "Content-Type: application/json"
        })
        Call<DFUser> login(@Body DFCredantials credantials);

        @POST("db/_table/channels")
        @Headers({"X-DreamFactory-Api-Key: " + APP_KEY_DF,
                "Content-Type: application/json"
        })
        Call<DFResponse<DFChannel>> createChannel(@Header("X-DreamFactory-Session-Token") String sessionToken,
                                                  @Body DFRequest channels);

        /*@POST("db/_table/urls")
        @Headers({"X-DreamFactory-Api-Key: " + APP_KEY_DF,
                "Content-Type: application/json"
        })
        Call<DFChannelResponse> createUrl(@Header("X-DreamFactory-Session-Token") String sessionToken,
                                          @Body List<DFUrl> url);
                                          */
        @GET("db/_table/channels?related=url_by_channel_id")
        @Headers({"X-DreamFactory-Api-Key: " + APP_KEY_DF,
                "Content-Type: application/json"
        })
        Call<DFResponse<DFChannel>> getChannels(@Header("X-DreamFactory-Session-Token") String sessionToken);

    }
}
