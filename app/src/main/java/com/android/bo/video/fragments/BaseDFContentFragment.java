package com.android.bo.video.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bo.video.R;
import com.android.bo.video.activities.PlayerActivity;
import com.android.bo.video.adapters.ChannelsAdapter;
import com.android.bo.video.adapters.DFChannelsAdapter;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.dreamfactory.DFUrlByChannelId;
import com.android.bo.video.dreamfactory.RESTClient;
import com.android.bo.video.interfaces.ItemClickSupport;
import com.android.bo.video.interfaces.OnChannelUrlClickListener;
import com.android.bo.video.models.Channel;
import com.android.bo.video.models.Channels;
import com.android.bo.video.network.ApiClient;
import com.android.bo.video.network.ApiError;
import com.android.bo.video.network.ApiListener;
import com.android.bo.video.utils.DividerItemDecoration;
import com.android.bo.video.utils.SpaceItemDecoration;
import com.android.bo.video.utils.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseDFContentFragment extends BaseFragment implements OnChannelUrlClickListener {

    private static final String ACTION_BAR_NAME = "actionBarName";
    private static final String CHANNELS_TAG = "Channel";
    private Channels<DFChannel> channels = new Channels<>();
    private DFChannelsAdapter adapter;
    private String name;

    @Override
    public void onChannelUrlClick(String url, DFChannel dfChannel) {
        startActivity(PlayerActivity.getLaunchPlayerActivity(getActivity(), dfChannel, url));
        hideKeyboard();
    }

    public class DFChannelNameComparator implements Comparator<DFChannel> {
        @Override
        public int compare(DFChannel o1, DFChannel o2) {
            return o1.getChannelName().compareToIgnoreCase(o2.getChannelName());
        }
    }

    public static Bundle createFragment(String name, Channels<DFChannel> channels) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION_BAR_NAME, name);
        bundle.putParcelableArrayList(CHANNELS_TAG, channels);
        return bundle;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (bar != null && name != null)
            bar.setTitle(name);
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
            name = getArguments().getString(ACTION_BAR_NAME);
            setupActionBar();
            ArrayList<DFChannel> dfChannels = getArguments().getParcelableArrayList(CHANNELS_TAG);
            if (dfChannels != null) {
                channels.addAll(dfChannels);
            }
        }
    }

    private void initFragmentData(View view) {
        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            if (channels == null)
                channels = new Channels<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new DFChannelsAdapter(channels, getActivity(), this);
            recyclerView.addItemDecoration(new SpaceItemDecoration(10, true, true));
            recyclerView.setAdapter(adapter);
        }
    }

    private void showDialog(final DFChannel channel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.open_dialog_title));
        int size = channel.getUrlByChannelId().size();
        ArrayList<String> streams = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            streams.add("Stream " + (i + 1));
        }
        CharSequence actions[] = streams.toArray(new CharSequence[size]);
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<DFUrlByChannelId> dfUrlByChannelIds = channel.getUrlByChannelId();
                startActivity(PlayerActivity.getLaunchPlayerActivity(getActivity(), channel, dfUrlByChannelIds.get(which).getUrl()));
                hideKeyboard();
            }
        });
        builder.show();
    }

    public void doSearch(String searchData) {
        if (!TextUtils.isEmpty(searchData)) {
            String query = searchData.toLowerCase();
            final Channels<DFChannel> filteredModelList = new Channels<>();
            for (DFChannel model : channels) {
                final String text = model.getChannelName().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }

            Collections.sort(filteredModelList, new DFChannelNameComparator());
            adapter.setChannels(filteredModelList);
        } else {
            Collections.sort(channels, new DFChannelNameComparator());
            adapter.setChannels(channels);
        }
    }
}
