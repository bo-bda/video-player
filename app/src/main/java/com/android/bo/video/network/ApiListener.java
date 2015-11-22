package com.android.bo.video.network;

import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class ApiListener<T> extends ClientListener {


    private Class<T> type;

    public ApiListener(Class<T> type) {
        this.type = type;
    }

    public ApiListener(Activity activity, Class<T> type) {
        super(activity);
        this.type = type;
    }

    public ApiListener(Fragment fragment, Class<T> type) {
        super(fragment);
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }


    public final void deliverSuccess(final T result) {
        deliver(new Runnable() {
            @Override
            public void run() {
                onSuccess(result);
            }
        });
    }

    public abstract void onSuccess(T result);
}
