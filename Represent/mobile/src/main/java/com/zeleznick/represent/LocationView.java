package com.zeleznick.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.google.android.gms.location.LocationServices;

public class LocationView extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    int selected;
    Button locationButton;

    protected LocationManager mLocationManager;
    protected boolean gps_enabled, network_enabled;

    Location myLocation;
    GoogleApiClient mGoogleApiClient;

    static LocationView init(int val) {
        LocationView locationView = new LocationView();
        Log.i("Location View", "init");
        return locationView;
    }
    /*
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double mLat = location.getLatitude();
            double mLong = location.getLongitude();
            Log.i("LV", "Location Changed to " + mLat + ", " + mLong);
            myLocation = new Location(mLat, mLong);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude", "status");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }*/
        // Create an instance of GoogleAPIClient.
        Log.i("Location View", "create");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
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
                RepsListFragment.init(1);
            }
        });
        Log.i("Location View", "View created");
        return rootView;
    }

}
