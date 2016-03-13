package com.zeleznick.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RepView extends FragmentActivity {
    int selected;
    Button backButton;
    ImageView imgIcon;
    TextView endDateHeader;
    TextView committeeHeader;
    TextView recentBillsHeader;
    EditText endDate;
    EditText committees;
    EditText recentBills;

    final Representative reps[] = Representative.getBaseReps();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detailed View");
        Bundle bundle = getIntent().getExtras();
        int repNumber = bundle.getInt("repNumber");

        Log.i("Rep View", "loaded with rep " + repNumber);

        Representative rep = reps[repNumber];
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        imgIcon.setImageResource(rep.icon);

        endDateHeader = (TextView) findViewById(R.id.endDateHeader);
        committeeHeader = (TextView) findViewById(R.id.committeeHeader);
        recentBillsHeader = (TextView) findViewById(R.id.recentBillsHeader);

        endDate = (EditText) findViewById(R.id.endDate);
        endDate.setText("UNTIL DEATH");
        committees = (EditText) findViewById(R.id.committees);
        committees.setText("Food, Arts & Crafts, Decorating, Baking");
        recentBills = (EditText) findViewById(R.id.recentBills);
        recentBills.setText("POOPING, SLEEPING, TAX FRAUD");

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
