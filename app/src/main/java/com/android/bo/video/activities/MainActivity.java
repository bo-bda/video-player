package com.android.bo.video.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.android.bo.video.R;
import com.android.bo.video.fragments.BaseContentFragment;
import com.android.bo.video.utils.Types;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    private SearchView mSearchView;
    private MenuItem searchMenuItem;

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
                if (searchMenuItem != null && searchMenuItem.isActionViewExpanded()) {
                    searchMenuItem.collapseActionView();
                    hideKeyboard();
                    mSearchView.setQuery(null, false);
                }
            }
        });
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);

        String ukraineTabName = getString(R.string.Ukraine);
        mTabHost.addTab(mTabHost.newTabSpec(ukraineTabName).setIndicator(ukraineTabName),
                BaseContentFragment.class, BaseContentFragment.createFragment(ukraineTabName, Types.Tabs.Ukraine));

        String russianTabName = getString(R.string.Russian);
        mTabHost.addTab(mTabHost.newTabSpec(russianTabName).setIndicator(russianTabName),
                BaseContentFragment.class, BaseContentFragment.createFragment(russianTabName, Types.Tabs.Russian));

        String allTabName = getString(R.string.all);
        mTabHost.addTab(mTabHost.newTabSpec(allTabName).setIndicator(allTabName),
                BaseContentFragment.class, BaseContentFragment.createFragment(allTabName, Types.Tabs.All));

        String favouritesTabName = getString(R.string.Favourites);
        mTabHost.addTab(mTabHost.newTabSpec(favouritesTabName).setIndicator(favouritesTabName),
                BaseContentFragment.class, BaseContentFragment.createFragment(favouritesTabName, Types.Tabs.Favourite));
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.tabFrameLayout);
        if (currentFragment != null && currentFragment instanceof BaseContentFragment) {
            ((BaseContentFragment) currentFragment).doSearch(query);
        }
    }


    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }


}
