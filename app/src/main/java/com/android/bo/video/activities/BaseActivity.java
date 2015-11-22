package com.android.bo.video.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.bo.video.interfaces.ProgressDialogInterface;

/*
 * Created by Bo on 21.11.2015.
 */
public class BaseActivity extends AppCompatActivity implements ProgressDialogInterface {

    private ProgressDialog progressDialog;

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = this.getCurrentFocus();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.clearFocus();
        }
    }

    @Override
    public void showProgress(@Nullable String progressMessage) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(progressMessage);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isProgressShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }
}
