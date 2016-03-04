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
public class VoteView extends Fragment {

    int repNumber;
    ImageView imgIcon;
    TextView repName;
    static Representative reps[] = Representative.getBaseReps();

    static VoteView init(int val) {
        VoteView voteView = new VoteView();
        if ( val >= reps.length ) {
            Log.i("Vote View", "ERROR INIT SET WITH " + val);
            val = 0;
        }
        voteView.repNumber = val;
        Log.i("Vote View", "init with value " + val);
        return voteView;
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
                R.layout.election_view, container, false);
        Bundle args = getArguments();
        Log.i("Vote View", "View created for " + repNumber);
        return rootView;
    }

}
