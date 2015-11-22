package com.android.bo.video.m3uparser;

import android.text.TextUtils;

import com.android.bo.video.models.Channel;
import com.android.bo.video.models.Channels;
import com.android.bo.video.types.Types;

/*
 * Created by Bo on 21.11.2015.
 */
public class M3UParser {

    public Channels parseFile(String stream, Types.Uris uri) {
        Channels channels = new Channels();
        if (!TextUtils.isEmpty(stream)) {
            stream = stream.replaceAll("#EXTM3U", "").trim();
            String[] arr = stream.split("#EXTINF.*,");
            int startIndex = uri.getStartIndex();
            int endIndex = uri.getEndIndex();
            if (arr.length > startIndex && arr.length - endIndex > 0)
                for (int n = startIndex; n < arr.length - endIndex; n++) {
                    if (arr[n].contains("http") || arr[n].contains("rtmp") || arr[n].contains("rtsp")) {
                        String[] nu = arr[n].split(uri.getSeparator());
                        if (nu.length == 2 && (uri.getIgnorePremium().isEmpty() || !nu[0].contains(uri.getIgnorePremium())))
                            channels.add(new Channel(nu[0], nu[1]));
                    }
                }
        }
        return channels;
    }
}
