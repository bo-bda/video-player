package com.android.bo.video.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bo.video.BoApplication;
import com.android.bo.video.R;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.dreamfactory.DFUrlByChannelId;
import com.android.bo.video.interfaces.OnChannelUrlClickListener;

import java.util.ArrayList;

/*
 * Created by Bo on 08.03.2016.
 */
public class UrlsAdapter extends RecyclerView.Adapter<UrlsAdapter.ViewHolder> {

    private DFChannel channel;
    private OnChannelUrlClickListener onChannelUrlClickListener;
    private ArrayList<DFUrlByChannelId> urlByChannelIds;
    private LayoutInflater inflater;

    public UrlsAdapter(DFChannel channel, Activity activity, OnChannelUrlClickListener onChannelUrlClickListener) {
        this.channel = channel;
        this.urlByChannelIds = channel.getUrlByChannelId();
        this.inflater = activity.getLayoutInflater();
        this.onChannelUrlClickListener = onChannelUrlClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_recycleview_urls, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DFUrlByChannelId item = urlByChannelIds.get(position);
        holder.isWork.setImageResource(item.isWork() ? R.drawable.green_shape_color : R.drawable.red_shape_color);
        holder.urlNumber.setText(String.format(BoApplication.getAppContext().getString(R.string.stream_number), position + 1));
        holder.urlNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChannelUrlClickListener != null) {
                    onChannelUrlClickListener.onChannelUrlClick(item.getUrl(), channel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlByChannelIds == null ? 0 : urlByChannelIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView isWork;
        public TextView urlNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            isWork = (ImageView) itemView.findViewById(R.id.isWork_row_recyclerview_urls);
            urlNumber = (TextView) itemView.findViewById(R.id.urlNumber_row_recyclerview_urls);
        }
    }
}
