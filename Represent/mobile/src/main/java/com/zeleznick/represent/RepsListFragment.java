package com.zeleznick.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RepsListFragment extends ListFragment {
    int fragNum;
    int selected;
    ListView listView1;


    private RepAdapter mAdapter;
    /*
    public String firstName;
    public String lastName;
    public String party;
    public String email;
    public String twitterHandle;
    public String website;/
     */
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


    static RepsListFragment init(int val) {
        RepsListFragment repList = new RepsListFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        repList.setArguments(args);
        return repList;
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        Log.i("init_tag", "Found bundle value: " + fragNum);
        base_values = makeBase();
        mAdapter = new RepAdapter(getActivity(), R.layout.listview_item_row, reps, base_values);
    }

    /**
     * The Fragment's UI is a simple text view showing its instance number and
     * an associated list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.main,
                container, false);
        listView1 = (ListView) rootView.findViewById(android.R.id.list);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Log.i("my_tag", "Checking for mAdapter: " + mAdapter);
        Log.i("my_tag", "Current fragment number: " + fragNum);
        listView1.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("Zach FragmentList", "Item clicked: " + id);
        Intent intent = new Intent(getContext(), RepView.class);
        startActivity(intent);
    }
}
