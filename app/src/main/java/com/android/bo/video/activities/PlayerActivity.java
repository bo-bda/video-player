package com.android.bo.video.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Window;
import android.view.WindowManager;

import com.android.bo.video.R;
import com.android.bo.video.models.Channel;
import com.android.bo.video.stream.AndroidMediaController;
import com.android.bo.video.stream.IjkVideoView;


/*
 * Created by Bo on 21.11.2015.
 */
public class PlayerActivity extends BaseActivity {

    private final static String CHANNEL_TAG = "channel";
    private final static String CHANNEL_URI_TAG = "channelUri";
    private IjkVideoView ijkVideoView;
    private AndroidMediaController mMediaController;

    public static Intent getLaunchPlayerActivity(Context context, Channel channel, String url) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(CHANNEL_TAG, channel);
        intent.putExtra(CHANNEL_URI_TAG, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Channel channel = getIntent().getParcelableExtra(CHANNEL_TAG);
        String url = getIntent().getStringExtra(CHANNEL_URI_TAG);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(channel.getName());
        mMediaController = new AndroidMediaController(this, false);

        ijkVideoView = (IjkVideoView) findViewById(R.id.player);
        ijkVideoView.setVideoPath(url);

        ijkVideoView.setMediaController(mMediaController);
        ijkVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ijkVideoView != null)
            ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ijkVideoView != null && !ijkVideoView.isPlaying()) {
            ijkVideoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ijkVideoView != null)
            ijkVideoView.stopPlayback();
    }
}
