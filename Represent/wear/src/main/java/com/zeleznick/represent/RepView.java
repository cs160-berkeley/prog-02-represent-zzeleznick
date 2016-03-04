package com.zeleznick.represent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zeleznick on 3/3/16.
 */
public class RepView extends Fragment {

    int repNumber;
    ImageView imgIcon;
    TextView repName;
    static Representative reps[] = Representative.getBaseReps();

    static RepView init(int val) {
        RepView repView = new RepView();
        if ( val >= reps.length ) {
            Log.i("Rep View", "ERROR INIT SET WITH " + val);
            val = 0;
        }
        repView.repNumber = val;
        Log.i("Rep View", "init with value " + val);
        return repView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Bundle bundle = getIntent().getExtras();
        int repNumber = bundle.getInt("repNumber");
        Log.i("Rep View", "loaded with rep " + repNumber);
        */
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.rep_view, container, false);
        Bundle args = getArguments();
        Representative rep = reps[repNumber];
        imgIcon = (ImageView) rootView.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(rep.icon);
        repName = (TextView) rootView.findViewById(R.id.repName);
        String fullName = rep.firstName + " " + rep.lastName;
        repName.setText(fullName);

        Log.i("Rep View", "View created for " + repNumber + " and name " + fullName);
        return rootView;
    }

}
