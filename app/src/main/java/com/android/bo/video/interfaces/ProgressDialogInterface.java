package com.android.bo.video.interfaces;

import android.support.annotation.Nullable;

/*
 * Created by Bo on 21.11.2015.
 */
public interface ProgressDialogInterface {

    void showProgress(@Nullable String progressMessage);

    void dismissProgress();

    boolean isProgressShowing();
}