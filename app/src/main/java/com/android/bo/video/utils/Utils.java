package com.android.bo.video.utils;

import android.text.TextUtils;
import android.util.Log;

import com.android.bo.video.BoApplication;
import com.android.bo.video.dreamfactory.RESTClient;
import com.android.bo.video.m3uparser.M3UParser;
import com.android.bo.video.models.Channel;
import com.android.bo.video.models.Channels;
import com.android.bo.video.network.ApiClient;
import com.android.bo.video.network.ApiError;
import com.android.bo.video.network.ApiListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/*
 * Created by Bo on 07.03.2016.
 */
public class Utils {

    private static Channels<Channel> channels = new Channels<>();
    private static int countBusy;
    private static int currentRequestSize;

    private static class ChannelNameComparator implements Comparator<Channel> {
        @Override
        public int compare(Channel o1, Channel o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public static void updateAll(){
        countBusy = 0;
        currentRequestSize = Types.Local.size() + Types.AllChannels.size();
        readFromFile(Types.Local);
        updateUrls(Types.AllChannels);
    }

    private static void readFromFile(ArrayList<Types.Uris> uris) {
        try {
            for (Types.Uris uri : uris) {
                Channels<Channel> newChannels = new Channels<>();
                StringBuilder buf = new StringBuilder();
                InputStream file = BoApplication.getAppContext().getAssets().open(uri.getUri());
                BufferedReader in = new BufferedReader(new InputStreamReader(file, "UTF-8"));
                String str;
                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }
                in.close();
                String string = buf.toString();
                if (!TextUtils.isEmpty(string)) {
                    M3UParser m3UParser = new M3UParser();
                    newChannels = m3UParser.parseFile(string, uri);
                }
                mergeChannels(newChannels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateUrls(final ArrayList<Types.Uris> uris) {
        for (Types.Uris uri : uris) {
            final String url = uri.getUri();
            ApiClient.getSharedInstance().getChannels(uri, new ApiListener<Channels>(Channels.class) {
                @Override
                public void onSuccess(Channels result) {
                    mergeChannels(result);
                    Log.e("MyTag", "onSuccess: " + url);
                }

                @Override
                public void onError(ApiError error) {
                    mergeChannels(new Channels<Channel>());
                    Log.e("MyTag", "onError: " + url);
                }
            });
        }

    }

    private static void mergeChannels(Channels<Channel> newChannels) {
        countBusy++;
        for (Channel channel : newChannels) {
            if (channels.contains(channel)) {
                HashSet<String> uri = channel.getUri();
                if (!channel.getUri().contains(uri))
                    channels.get(channels.indexOf(channel)).addUri(new ArrayList<>(uri));
            } else {
                channels.add(channel);
            }
        }
        Collections.sort(channels, new ChannelNameComparator());
        if (countBusy == currentRequestSize) {
            RESTClient.getInstance().sync(channels);
            Log.e("MyTag", "total channels = " + channels.size());
        }
    }
}
