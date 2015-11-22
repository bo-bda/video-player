package com.android.bo.video.network;

import com.android.bo.video.BoApplication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;

public abstract class Client extends HandlerThread {
    public static final String NO_INTERNET_ERROR = "No internet connection";

    public Client(String name) {
        super(name);
    }

    protected boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) BoApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
