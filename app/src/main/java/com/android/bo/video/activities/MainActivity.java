package com.android.bo.video.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.android.bo.video.R;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.fragments.BaseDFContentFragment;
import com.android.bo.video.models.Channels;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    private final static String CHANNELS_TAG = "Channel";

    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    private Channels<DFChannel> dfChannels = new Channels<>();

    public static Intent getLaunchMainActivity(Context context, Channels<DFChannel> channels) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putParcelableArrayListExtra(CHANNELS_TAG, channels);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        if (getIntent() != null && getIntent().getExtras() != null) {
            ArrayList<DFChannel> channels = getIntent().getParcelableArrayListExtra(CHANNELS_TAG);
            dfChannels.addAll(channels);
        }
        setupToolbar();
        setUpTabHost();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);
    }


    private void setUpTabHost() {
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                hideSearch();
            }
        });
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        String ukraineTabName = getString(R.string.Ukraine);
        mTabHost.addTab(mTabHost.newTabSpec(ukraineTabName).setIndicator(ukraineTabName),
                BaseDFContentFragment.class, BaseDFContentFragment.createFragment(ukraineTabName, dfChannels));

        String russianTabName = getString(R.string.Russian);
        mTabHost.addTab(mTabHost.newTabSpec(russianTabName).setIndicator(russianTabName),
                BaseDFContentFragment.class, BaseDFContentFragment.createFragment(russianTabName, dfChannels));

        String allTabName = getString(R.string.all);
        mTabHost.addTab(mTabHost.newTabSpec(allTabName).setIndicator(allTabName),
                BaseDFContentFragment.class, BaseDFContentFragment.createFragment(allTabName, dfChannels));

        String favouritesTabName = getString(R.string.Favourites);
        mTabHost.addTab(mTabHost.newTabSpec(favouritesTabName).setIndicator(favouritesTabName),
                BaseDFContentFragment.class, BaseDFContentFragment.createFragment(favouritesTabName, dfChannels));
    }

    public void hideSearch() {
        if (searchMenuItem != null && searchMenuItem.isActionViewExpanded()) {
            searchMenuItem.collapseActionView();
            hideKeyboard();
            mSearchView.setQuery(null, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mSearchView.setLayoutParams(params);
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
//        startMode(Mode.NORMAL);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        doSearch(newText);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        mSearchView.requestFocus();
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(mSearchView.getQuery().toString());
            }
        });
        return true;
    }

    private void doSearch(String query) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(android.R.id.tabcontent);
//        if (currentFragment != null && currentFragment instanceof BaseContentFragment) {
//            ((BaseContentFragment) currentFragment).doSearch(query);
//        } else
        if (currentFragment != null && currentFragment instanceof BaseDFContentFragment) {
            ((BaseDFContentFragment) currentFragment).doSearch(query);
        }
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }
}
