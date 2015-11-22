package com.android.bo.video.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.android.bo.video.R;
import com.android.bo.video.fragments.BaseContentFragment;
import com.android.bo.video.types.Types;

public class MainActivity extends BaseActivity{


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
        setUpTabHost();
    }


    private void setUpTabHost() {
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
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
}
