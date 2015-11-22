package com.android.bo.video.fragments;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.bo.video.activities.BaseActivity;
import com.android.bo.video.interfaces.ProgressDialogInterface;

/*
 * Created by Bo on 21.11.2015.
 */
public class BaseFragment extends Fragment implements ProgressDialogInterface {


    @Nullable
    public BaseActivity getBaseActivity() {
        BaseActivity bActivity = null;
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            bActivity = (BaseActivity) activity;
        }
        return bActivity;
    }

    @Override
    public void showProgress(@Nullable String progressMessage) {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.showProgress(progressMessage);
        }
    }

    @Override
    public void dismissProgress() {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.dismissProgress();
        }
    }

    @Override
    public boolean isProgressShowing() {
        BaseActivity bActivity = getBaseActivity();
        return bActivity != null && bActivity.isProgressShowing();
    }

    public void hideKeyboard() {
        BaseActivity bActivity = getBaseActivity();
        if (bActivity != null) {
            bActivity.hideKeyboard();
        }
    }
}
