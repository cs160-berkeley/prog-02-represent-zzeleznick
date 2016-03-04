package com.zeleznick.represent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.wearable.view.CardFrame;
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

    int districtNumber = 0;
    ImageView imgIcon;
    TextView district;
    TextView firstCandidate;
    TextView firstCandidateValue;
    TextView secondCandidate;
    TextView secondCandidateValue;
    CardFrame card;

    static Representative reps[] = Representative.getBaseReps();

    static VoteView init(int val) {
        VoteView voteView = new VoteView();
        voteView.districtNumber = val;
        Log.i("Vote View", "init with value " + val);
        return voteView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        districtNumber = args.getInt("district", 0);
        // Bundle bundle = getIntent().getExtras();
        Log.i("Vote View", "loaded with rep " + districtNumber);

    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.election_view, container, false);

        final Bundle args = getArguments();
        districtNumber = args.getInt("district", 0);

        Log.i("Vote View", "View created for " + districtNumber);
        district = (TextView) rootView.findViewById(R.id.district);
        firstCandidate = (TextView) rootView.findViewById(R.id.firstCandidate);
        firstCandidateValue = (TextView) rootView.findViewById(R.id.firstCandidateValue);
        secondCandidate = (TextView) rootView.findViewById(R.id.secondCandidate);
        secondCandidateValue = (TextView) rootView.findViewById(R.id.secondCandidateValue);

        district.setText("D" + districtNumber);
        firstCandidate.setText("OBAMA");
        firstCandidateValue.setText(55 + districtNumber + "%");
        secondCandidate.setText("ROMNEY");
        secondCandidateValue.setText(45 - districtNumber + "%");
        card = (CardFrame) rootView.findViewById(R.id.myCard);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Vote View", "Card Clicked");

            }
        });

        return rootView;

    }

}
