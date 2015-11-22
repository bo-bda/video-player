package com.android.bo.video.network;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public abstract class ClientListener {

    private WeakReference<Activity> activity;
    private WeakReference<Fragment> fragment;

    protected boolean isServiceController = false;

    public ClientListener() {
    }

    public ClientListener(Activity activity) {
        attachActivity(activity);
    }

    public ClientListener(Fragment fragment) {
        attachFragment(fragment);
    }



    public void attachActivity(Activity activity) {
        if (activity != null) {
            this.activity = new WeakReference<>(activity);
            isServiceController = false;
        }
    }

    public void attachFragment(Fragment fragment) {
        if (fragment != null) {
            this.fragment = new WeakReference<>(fragment);
            isServiceController = false;
        }
    }



    protected Activity getActivity() {
        if (activity != null) {
            Activity act = activity.get();
            if (act != null && !act.isFinishing()) {
                return act;
            }
        } else if (fragment != null) {
            Fragment frag = fragment.get();
            if (frag != null && frag.isAdded()) {
                return frag.getActivity();
            }
        }
        return null;
    }


    protected void deliver(Runnable action) {

        deliverToActivity(action);

    }

    private void deliverToActivity(Runnable action) {
        Activity act = getActivity();
        if (act != null) {
            act.runOnUiThread(action);
        } else {
            action.run();
        }
    }


    public final void deliverError(final ApiError error) {
        deliver(new Runnable() {
            @Override
            public void run() {
                onError(error);
            }
        });
    }

    public abstract void onError(final ApiError error);
}