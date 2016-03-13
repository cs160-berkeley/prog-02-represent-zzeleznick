package com.zeleznick.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationView extends Fragment {

    int selected;
    Button locationButton;


    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";

    static LocationView init(int val) {
        LocationView locationView = new LocationView();
        Log.i("Location View", "init");
        return locationView;
    }

    public void goToMain() {
        // swap fragments
        MainActivity.SwiperAdapter mSwiperAdapter = new MainActivity.SwiperAdapter(getActivity().getSupportFragmentManager());
        ViewPager mPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mPager.setAdapter(mSwiperAdapter);
        mPager.setCurrentItem(1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(
                R.layout.locationview, container, false);
        Bundle args = getArguments();

        final EditText zipcodeText = (EditText) rootView.findViewById(R.id.zipcode);
        zipcodeText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String foundText = "" +zipcodeText.getText();
                    if (foundText.length() != 5) {
                        Toast.makeText(getContext(), "Not a Valid Zipcode", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Found " + foundText, Toast.LENGTH_SHORT).show();
                        goToMain();
                    }
                    return true;
                }
                return false;
            }
        });

        locationButton = (Button) rootView.findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Location Found", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(getContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");
                Log.i("Location View", "About to Feed Fred");
                getActivity().startService(sendIntent);
                goToMain();
            }
        });
        Log.i("Location View", "View created");
        return rootView;
    }

}
