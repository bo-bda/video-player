package com.android.bo.video.stream;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;

import java.util.ArrayList;

/**
 * Created by Bo on 9/16/15.
 */
public class AndroidMediaController extends MediaController implements IMediaController {
    private ActionBar mActionBar;
    private AppCompatActivity activity;

    public AndroidMediaController(AppCompatActivity activity, AttributeSet attrs) {
        super(activity, attrs);
        initView(activity);
    }

    public AndroidMediaController(AppCompatActivity activity, boolean useFastForward) {
        super(activity, useFastForward);
        initView(activity);
    }

    public AndroidMediaController(AppCompatActivity activity) {
        super(activity);
        initView(activity);
    }

    private void initView(AppCompatActivity activity) {
        this.activity = activity;
        setSupportActionBar(activity.getSupportActionBar());
    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
        if (actionBar != null) {
            if (isShowing()) {
                actionBar.show();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

            } else {
                actionBar.hide();

            }
        }
    }

    @Override
    public void show() {
        super.show();
        if (mActionBar != null)
            mActionBar.show();
    }

    @Override
    public void hide() {
        super.hide();
        if (mActionBar != null)
            mActionBar.hide();
        for (View view : mShowOnceArray)
            view.setVisibility(View.GONE);
        mShowOnceArray.clear();
        if (activity != null) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //----------
    // Extends
    //----------
    private ArrayList<View> mShowOnceArray = new ArrayList<View>();

    public void showOnce(@NonNull View view) {
        mShowOnceArray.add(view);
        view.setVisibility(View.VISIBLE);
        show();
    }
}
