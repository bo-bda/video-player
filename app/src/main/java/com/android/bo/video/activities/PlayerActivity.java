package com.android.bo.video.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.bo.video.R;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.dreamfactory.DFRequest;
import com.android.bo.video.dreamfactory.DFUrlByChannelId;
import com.android.bo.video.dreamfactory.RESTClient;
import com.android.bo.video.models.Channel;
import com.android.bo.video.stream.AndroidMediaController;
import com.android.bo.video.stream.IjkVideoView;
import com.android.bo.video.utils.Storage;

import java.util.Collections;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;


/*
 * Created by Bo on 21.11.2015.
 */
public class PlayerActivity extends BaseActivity {

    private final static String CHANNEL_TAG = "channel";
    private final static String DF_CHANNEL_TAG = "DFChannel";
    private final static String CHANNEL_URI_TAG = "channelUri";
    private IjkVideoView ijkVideoView;
    private AndroidMediaController mMediaController;
    private ProgressBar progressBar;
    private String urlPlay;
    private DFChannel dfChannel;

    public static Intent getLaunchPlayerActivity(Context context, Channel channel, String url) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(CHANNEL_TAG, channel);
        intent.putExtra(CHANNEL_URI_TAG, url);
        return intent;
    }

    public static Intent getLaunchPlayerActivity(Context context, DFChannel channel, String url) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(DF_CHANNEL_TAG, channel);
        intent.putExtra(CHANNEL_URI_TAG, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if (getIntent() != null && getIntent().getExtras() != null) {

            dfChannel = getIntent().getParcelableExtra(DF_CHANNEL_TAG);
            Channel channel = getIntent().getParcelableExtra(CHANNEL_TAG);
            urlPlay = getIntent().getStringExtra(CHANNEL_URI_TAG);

            if (getSupportActionBar() != null) {
                String name = channel == null ? dfChannel.getChannelName() : channel.getName();
                getSupportActionBar().setTitle(name);
            }
            mMediaController = new AndroidMediaController(this, false);

            progressBar = (ProgressBar) findViewById(R.id.progressbar);

            ijkVideoView = (IjkVideoView) findViewById(R.id.player);
            ijkVideoView.setVideoPath(urlPlay);

            ijkVideoView.setMediaController(mMediaController);
            ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    updateChannelState(dfChannel, true);
                }
            });
            ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer mp) {
                    updateChannelState(dfChannel, false);
                    finish();
                }
            });
            ijkVideoView.start();
        }
    }

    private void updateChannelState(DFChannel dfChannel, boolean isWork) {
        String sessionToken = Storage.getInstance().getUserProfile().getSessionToken();
        DFUrlByChannelId urlByChannelId = null;
        for (DFUrlByChannelId dfUrlByChannelId : dfChannel.getUrlByChannelId()) {
            if (dfUrlByChannelId.getUrl().equalsIgnoreCase(urlPlay)) {
                dfUrlByChannelId.setIsWork(isWork);
                urlByChannelId = dfUrlByChannelId;
                break;
            }
        }
        if (urlByChannelId != null) {
            DFRequest<DFUrlByChannelId> url = new DFRequest<>(Collections.singletonList(urlByChannelId));
            RESTClient.getInstance().updateChannel(sessionToken, urlByChannelId.getId(), url);
        }
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
