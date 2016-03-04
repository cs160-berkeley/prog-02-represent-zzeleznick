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

public class MainActivity extends FragmentActivity {
    static final int ITEMS = 4;
    SwiperAdapter mSwiperAdapter;
    ViewPager mPager;
    TextView repName;

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0.   Create SwiperAdapter
        mSwiperAdapter = new SwiperAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mSwiperAdapter);

        /* Acceleration onCreate */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                Log.d("WEAR MAIN", "shake detected.");
                Toast.makeText(getApplicationContext(), "Shake!", Toast.LENGTH_SHORT).show();
            }
        });
        /* End Acceleration onCreate */

        Log.d("WEAR MAIN", "created.");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

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

    public static class SwiperAdapter extends FragmentPagerAdapter {
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
                    // can add stuff to bundle with put string
                    // then setArguments to Fragment before return
                    return RepView.init(position);
                case 1: // Fragment # 1 - This will show extra exercises
                    return RepView.init(position);
                case 2:
                    return RepView.init(position);
                case 3:
                    return VoteView.init(position);
                default:
                    return RepView.init(position);
            }
        }
    }
}
