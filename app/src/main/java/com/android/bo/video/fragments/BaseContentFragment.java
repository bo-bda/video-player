package com.android.bo.video.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bo.video.R;
import com.android.bo.video.activities.PlayerActivity;
import com.android.bo.video.adapters.ChannelsAdapter;
import com.android.bo.video.interfaces.ItemClickSupport;
import com.android.bo.video.models.Channel;
import com.android.bo.video.models.Channels;
import com.android.bo.video.network.ApiClient;
import com.android.bo.video.network.ApiError;
import com.android.bo.video.network.ApiListener;
import com.android.bo.video.types.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseContentFragment extends BaseFragment {

    private static final String ACTION_BAR_NAME = "actionBarName";
    private static final String TABS_TYPE = "tabType";
    private Types.Tabs type;
    private ArrayList<Channel> channels = new Channels();
    private ChannelsAdapter adapter;
    private int countBisy;
    private int currentRequestSize;

    public class ChannelNameComparator implements Comparator<Channel> {
        @Override
        public int compare(Channel o1, Channel o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public static Bundle createFragment(String name, Types.Tabs type) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION_BAR_NAME, name);
        bundle.putSerializable(TABS_TYPE, type);
        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initFragmentData(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String name = getArguments().getString(ACTION_BAR_NAME);
            ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (bar != null)
                bar.setTitle(name);
            type = (Types.Tabs) getArguments().get(TABS_TYPE);
        }

    }

    private void initFragmentData(View view) {
        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            channels = new ArrayList<>();
            switch (type) {
                case Ukraine:
                    getChannels(Types.UrkaineChannels);
                    break;

                case Russian:
                    getChannels(Types.RussainChannels);
                    break;

                case All:
                    getChannels(Types.AllChannels);
                    break;

                case Favourite:
                    break;

                default:
                    channels = new ArrayList<>();
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new ChannelsAdapter(channels, getActivity());
            recyclerView.setAdapter(adapter);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showDialog(channels.get(position));
                }
            });
        }
    }

    private void showDialog(final Channel channel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.open_dialog_title));
        int size = channel.getUri().size();
        ArrayList<String> streams = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            streams.add("Stream " + (i + 1));
        }
        CharSequence actions[] = streams.toArray(new CharSequence[size]);
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(PlayerActivity.getLaunchPlayerActivity(getActivity(), channel, (String) channel.getUri().toArray()[which]));
            }
        });
        builder.show();
    }

    private void getChannels(final ArrayList<Types.Uris> uris) {
        showProgress(null);
        countBisy = 0;
        currentRequestSize = uris.size();
        for (Types.Uris uri : uris) {
            ApiClient.getSharedInstance().getChannels(uri, new ApiListener<Channels>(Channels.class) {
                @Override
                public void onSuccess(Channels result) {
                    mergeChannels(result);
                }

                @Override
                public void onError(ApiError error) {
                    mergeChannels(new Channels());
                }
            });
        }
    }

    private void mergeChannels(Channels newChannels) {
        countBisy++;
        for (Channel channel : newChannels) {
            if (channels.contains(channel)) {
                channels.get(channels.indexOf(channel)).addUri(new ArrayList<>(channel.getUri()));
            } else {
                channels.add(channel);
            }
        }
        Collections.sort(channels, new ChannelNameComparator());
        adapter.setChannels(channels);
        if (countBisy == currentRequestSize) {
            dismissProgress();
        }
    }
}
