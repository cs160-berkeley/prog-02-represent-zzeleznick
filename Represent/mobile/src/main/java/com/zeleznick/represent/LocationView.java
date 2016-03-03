package com.zeleznick.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationView extends Fragment {

    int selected;

    ArrayList<Float> base_values = new ArrayList<>();

    public ArrayList<Float> makeBase() {
        base_values.add(0, 100f);
        base_values.add(1, 350f);
        base_values.add(2, 200f);
        base_values.add(3, 10f);
        base_values.add(4, 12f);
        base_values.add(5, 20f);
        return base_values;
    }


    static LocationView init(int val) {
        LocationView locationView = new  LocationView();
        Log.i("Location View", "init");
        return locationView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Location View", "created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.locationview, container, false);
        Bundle args = getArguments();
        Log.i("Location View", "View created");
        return rootView;
    }

}
