package com.gelecegiyazanlar.hocamnerede;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

//import com.google.android.gms.location.LocationListener;


public class Timeline extends Fragment {

    private FloatingActionButton sendLocation;

    private TextView txtOutputLatLon;
    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    float lat;
    float lon;
    View mView;

    public Timeline() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //LOGS SHOW THAT THIS IS ALWAYS CALLED WITH CORRECT VALUES
        super.onSaveInstanceState(savedInstanceState);
        /*
        savedInstanceState.putString("string", "example");
        savedInstanceState.putBoolean("boolean", bool);*/
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(getActivity(), "DEğiştiiii", Toast.LENGTH_SHORT).show();
            Log.d("Location Changed", "Lat : " + location.getLatitude());

            double latitude = location.getLatitude();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getActivity(), "status mü", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getActivity(), "Açıldıııı", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getActivity(), "Kapandııı", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_timeline, container, false);


        sendLocation = (FloatingActionButton) mView.findViewById(R.id.floatingActionButton);

        sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


                }
                else{
                    Toast.makeText(getActivity(),"Konum Servisiniz Kapalı",Toast.LENGTH_SHORT).show();
                }






                //Eğer Konum Servisi Açıksa Pop Up Göstersin
                    final AlertDialog.Builder mBuild = new AlertDialog.Builder(v.getContext());

                    final View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_up_message, null);
                    final EditText mMessage = (EditText) view.findViewById(R.id.editText);


                    txtOutputLatLon = (TextView) view.findViewById(R.id.text);

                    mBuild.setView(view);
                    final AlertDialog dialog = mBuild.create();

                    dialog.show();
                    Button sendMessage = (Button) view.findViewById(R.id.sendButton);

                    sendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mMessage.getText().toString().isEmpty()) {

                                mMessage.setText("");
                                getActivity().onBackPressed();
                                //view.setPressed(true);
                                dialog.dismiss();
                            }
                        }
                    });


            }
        });
        return mView;
    }






}
