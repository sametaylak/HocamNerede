package com.gelecegiyazanlar.hocamnerede;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class Timeline extends Fragment implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private FloatingActionButton sendLocation;
    private TextView text;

    private TextView txtOutputLatLon;
    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    float lat;
    float lon;
    View mView;

    public Timeline(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.pop_up_message, container, false);

        txtOutputLatLon = (TextView) mView.findViewById(R.id.text);
        buildGoogleApiClient();






        text = (TextView)mView.findViewById(R.id.text);
        sendLocation = (FloatingActionButton)mView.findViewById(R.id.floatingActionButton);

        sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuild = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_timeline, null);
                final EditText mMessage = (EditText)view.findViewById(R.id.editText);

                Button sendMessage = (Button)view.findViewById(R.id.sendButton);

                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mMessage.getText().toString().isEmpty()){

                        }
                    }
                });
                mBuild.setView(view);
                AlertDialog dialog = mBuild.create();
                dialog.show();
            }
        });
        return mView;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mView.getContext(), "İzin alınamadı....", Toast.LENGTH_SHORT).show();
            return;
        }

        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = (float) mLastLocation.getLatitude();
            lon = (float) mLastLocation.getLongitude();

            Toast.makeText(mView.getContext(),lat+" "+lon, Toast.LENGTH_LONG).show();

        }
        updateUI();

    }

    @Override
    public void onConnectionSuspended(int i) {
        buildGoogleApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (float) location.getLatitude();
        lon = (float) location.getLongitude();
        updateUI();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        Log.v("GEldim","heyyyy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(),"niden olmuyon",Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    void updateUI() {
        txtOutputLatLon.setText(lat+"\n"+lon);
    }
    @Override
    public void onClick(View v) {
        text.setText("Girdi 2");
    }
}
