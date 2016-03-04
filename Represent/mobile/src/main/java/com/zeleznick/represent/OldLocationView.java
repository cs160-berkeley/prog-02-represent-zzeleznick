package com.zeleznick.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
/*
public class OldLocationView extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    int selected;
    Button locationButton;

    protected LocationManager mLocationManager;
    protected boolean gps_enabled, network_enabled;

    Location myLocation;
    GoogleApiClient mGoogleApiClient;

    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";

    static OldLocationView init(int val) {
        OldLocationView locationView = new OldLocationView();
        Log.i("Location View", "init");
        return locationView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Location View", "create");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Wearable.API)
                    .build();
        }
    }

    private void sendMessage(final String path, final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                for (Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mGoogleApiClient, node.getId(), path, text.getBytes()).await();
                }
            }
        }).start();
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendMessage(START_ACTIVITY, "");

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (myLocation != null) {
            double zlat = myLocation.getLatitude();
            double zlong = myLocation.getLongitude();
            Log.i("LV", "Location Changed to " + zlat + ", " + zlong);
            // mLatitudeTextView.setText(String.valueOf(myLocation.getLatitude()));
            // mLongitudeTextView.setText(String.valueOf(myLocation.getLongitude()));
        } else {
            Toast.makeText(getContext(), "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LV", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LV", "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.locationview, container, false);
        Bundle args = getArguments();
        locationButton = (Button) rootView.findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Location Found", Toast.LENGTH_SHORT).show();
                if (myLocation != null) {
                    double zlat = myLocation.getLatitude();
                    double zlong = myLocation.getLongitude();
                    Log.i("LV", "Found location of" + zlat + ", " + zlong);
                }
                sendMessage(WEAR_MESSAGE_PATH, "blah");
                RepsListFragment.init(1);
            }
        });
        Log.i("Location View", "View created");
        return rootView;
    }

}
*/