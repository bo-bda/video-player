package com.android.bo.video.stream;

import android.view.View;
import android.widget.MediaController;

/**
 * Created by Bo on 9/16/15.
 */
public interface IMediaController {
    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean enabled);

    void setMediaPlayer(MediaController.MediaPlayerControl player);

    void show(int timeout);

    void show();

    //----------
    // Extends
    //----------
    void showOnce(View view);
}
