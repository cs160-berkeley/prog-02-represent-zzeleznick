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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RepView extends FragmentActivity {
    int selected;
    TextView unitsText;
    Button backButton;
    Context globalContext;

    final Representative reps[] = new Representative[]
            {
                    new Representative(R.drawable.peeta, "Peeta", "Mellark", "Tribute",
                            "peeta@hotmail.com", "peeta_m", "peetabread.com"),
                    new Representative(R.drawable.katniss, "Katniss", "Everdeen", "Tribute",
                            "kat_fire@hotmail.com", "kittykat", "kool-kat.com"),
                    new Representative(R.drawable.haymitch, "Haymitch", "Abernathy", "Victor",
                            "haymitch@aa.com", "still_drinking", "404.com")
            };

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detailed View");
        Log.i("Rep View", "loaded");
        globalContext = getBaseContext(); // getApplicationContext();

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
