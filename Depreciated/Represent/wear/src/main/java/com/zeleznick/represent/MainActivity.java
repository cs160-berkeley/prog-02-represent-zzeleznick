package com.zeleznick.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends FragmentActivity {
    static final int ITEMS = 4;
    static SwiperAdapter mSwiperAdapter;
    ViewPager mPager;
    TextView repName;

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("WEAR MAIN", "created.");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // 0.   Create SwiperAdapter
        mSwiperAdapter = new SwiperAdapter(getSupportFragmentManager(), new Bundle());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mSwiperAdapter);

        /* Acceleration onCreate */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                Log.d("WEAR MAIN", "shake detected.");
                int range = 10;
                int offset = 10;
                int num = offset + (int)(Math.random() * (range + 1));
                Toast.makeText(getApplicationContext(), "D" + num, Toast.LENGTH_SHORT).show();
                Log.i("WEAR MAIN", "Storing " + num);
                mSwiperAdapter.fragmentBundle.putInt("district", num);
                mSwiperAdapter.getItem(3);
                mPager.setCurrentItem(3);
                // mSwiperAdapter.notifyDataSetChanged();
                // mPager.destroyDrawingCache();
                // mPager.setCurrentItem(3);
            }
        });
        /* End Acceleration onCreate */

        if (extras != null) {
            String catName = extras.getString("CAT_NAME");
            Log.d("WEAR MAIN", "Received Name: " + catName);
            mPager.setCurrentItem(3);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WEAR MAIN", "OnResumeCalled");
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    public static class SwiperAdapter extends FragmentStatePagerAdapter {
        private final Bundle fragmentBundle;

        public SwiperAdapter(FragmentManager fragmentManager, Bundle data) {
            super(fragmentManager);
            fragmentBundle = data;
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("get_item", "Called with position: " + position);
            Fragment fragment;
            if (position == 0) {
                fragment = RepView.init(position);
            }
            else if (position == 1) {
                fragment = RepView.init(position);
            }
            else if (position == 2) {
                fragment =  RepView.init(position);
            }
            else if (position == 3) {
                fragment = VoteView.init(position);
            }
            else {
                fragment = RepView.init(position);
            }
            fragment.setArguments(mSwiperAdapter.fragmentBundle);
            return fragment;
        }
    }
}
