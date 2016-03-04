package com.zeleznick.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends FragmentActivity {
    static final int ITEMS = 3;
    SwiperAdapter mSwiperAdapter;
    ViewPager mPager;

    private GoogleApiClient mApiClient;

    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Represent!");
        // 0.   Create SwiperAdapter
        mSwiperAdapter = new SwiperAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mSwiperAdapter);
    }



    public static class SwiperAdapter extends FragmentStatePagerAdapter {
        public SwiperAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("get_item", "Called with position: " + position);
            switch (position) {
                case 0: // Fragment # 0 - This will show on default
                    // return RepsListFragment.init(position);
                    return LocationView.init(position);
                case 1: // Fragment # 1 - This will show extra exercises
                    return RepsListFragment.init(position);
                case 2:
                    return RepsListFragment.init(position);
                default:
                    // return RepsListFragment.init(position);
                    return LocationView.init(position);
            }
        }
    }

}
