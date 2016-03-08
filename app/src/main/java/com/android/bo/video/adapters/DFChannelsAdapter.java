package com.android.bo.video.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bo.video.R;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.interfaces.OnChannelUrlClickListener;
import com.android.bo.video.models.Channels;
import com.android.bo.video.views.FixedHeightLayoutManager;

/*
 * Created by Bo on 21.11.2015.
 */
public class DFChannelsAdapter extends RecyclerView.Adapter<DFChannelsAdapter.ViewHolder> {

    private Activity activity;
    private Channels<DFChannel> channels;
    private LayoutInflater inflater;
    private OnChannelUrlClickListener onChannelUrlClickListener;

    public DFChannelsAdapter(Channels<DFChannel> channels, Activity activity, OnChannelUrlClickListener onChannelUrlClickListener) {
        this.channels = channels;
        this.inflater = activity.getLayoutInflater();
        this.activity = activity;
        this.onChannelUrlClickListener = onChannelUrlClickListener;
    }

    public void setChannels(Channels<DFChannel> channels) {
        this.channels = channels;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_recycleview_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DFChannel item = channels.get(position);
        holder.image.setImageResource(getIcon(item.getChannelCountry()));
        holder.textViewName.setText(item.getChannelName());
        holder.staticRecyclerView.setLayoutManager(new FixedHeightLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        UrlsAdapter adapter = new UrlsAdapter(item, activity, onChannelUrlClickListener);
        holder.staticRecyclerView.setAdapter(adapter);
        //FIXME : is fav icon
//        holder.checkboxIsFav.setText(item.ge());
    }


    @Override
    public int getItemCount() {
        return channels == null ? 0 : channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView textViewName;
        public CheckBox checkboxIsFav;
        public RecyclerView staticRecyclerView;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.ChannelIcon);
            textViewName = (TextView) view.findViewById(R.id.ChannelName);
            checkboxIsFav = (CheckBox) view.findViewById(R.id.isChannelFavourite);
            staticRecyclerView = (RecyclerView) view.findViewById(R.id.urls_row_recyclerview_channel);
        }
    }

    public DFChannel getItemAtPosition(int position) {
        return channels != null && position < channels.size() ? channels.get(position) : null;
    }

    private int getIcon(String counrtyName) {
        if (counrtyName != null) {
            int icon;
            switch (counrtyName) {
                case "ua":
                    icon = R.drawable.ua;
                    break;

                case "russia":
                    icon = R.drawable.russia;
                    break;

                case "usa":
                    icon = R.drawable.usa;
                    break;

                default:
                case "unknown":
                    icon = R.drawable.unknown;
                    break;
            }
            return icon;
        }
        return R.drawable.unknown;
    }
}
