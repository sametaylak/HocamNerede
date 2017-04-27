package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.gelecegiyazanlar.hocamnerede.model.LocationPost;
import com.gelecegiyazanlar.hocamnerede.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Timeline extends Fragment {

    @BindView(R.id.shareLocationButton) FloatingActionButton shareLocationButton;
    @BindView(R.id.timelineRecyclerView) RecyclerView timelineRecyclerView;


    private LocationManager locationManager;
    private MaterialDialog locationProgress;
    private TimelineRecyclerAdapter locationAdapter;

    private List<LocationPost> locationPosts = new ArrayList<>();


    public Timeline() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, rootView);

        FirebaseHelper.getFirebaseUserDetail(new FirebaseHelper.FirebaseCallback() {
            @Override
            public void onSuccess(Object result) {
                User user = (User) result;
                if (user.isStudent()) {
                    shareLocationButton.setVisibility(View.GONE);
                }
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("locationPosts")
                        .orderByChild("userUniversity")
                        .equalTo(user.getUniversity())
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                locationPosts.add(0, dataSnapshot.getValue(LocationPost.class));
                                locationAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {}
                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
            }
        });

        setAdapter();

        return rootView;
    }

    private void setAdapter() {
        locationAdapter = new TimelineRecyclerAdapter(getContext(), locationPosts);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        timelineRecyclerView.setLayoutManager(mLayoutManager);
        timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        timelineRecyclerView.setAdapter(locationAdapter);
    }

    @OnClick(R.id.shareLocationButton)
    public void onShareLocationButtonClick() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            locationProgress = new MaterialDialog.Builder(getContext())
                    .title("Lütfen bekleyin")
                    .content("Konum bekleniyor...")
                    .progress(true, 0)
                    .progressIndeterminateStyle(true)
                    .cancelable(false)
                    .show();
        } else {
            new MaterialDialog.Builder(getContext())
                    .title("Dikkat!")
                    .content("Bu hizmeti kullanabilmeniz için Konum özelliğini aktifleştirmelisiniz.")
                    .contentGravity(GravityEnum.CENTER)
                    .positiveText("Tamam")
                    .iconRes(R.drawable.ic_error)
                    .show();
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            locationProgress.dismiss();


            locationAdapter.setLatLong(location);

            new MaterialDialog.Builder(getContext())
                    .title("Paylaş")
                    .content("Lütfen açıklama giriniz.")
                    .inputRangeRes(10, 50, R.color.colorPrimary)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("Açıklama", null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, final CharSequence input) {
                            FirebaseHelper.getFirebaseUserDetail(new FirebaseHelper.FirebaseCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    User user = (User) result;
                                    LocationPost post;
                                    if (user.getAvatar() != null) {
                                        post = new LocationPost(
                                                user.getFullname(),
                                                location.getLatitude(),
                                                location.getLongitude(),
                                                input.toString(),
                                                user.getUniversity(),
                                                user.getAvatar()
                                        );
                                    } else {
                                        post = new LocationPost(
                                                user.getFullname(),
                                                location.getLatitude(),
                                                location.getLongitude(),
                                                input.toString(),
                                                user.getUniversity(),
                                                null
                                        );
                                    }

                                    FirebaseHelper.saveLocationPost(post);
                                    new MaterialDialog.Builder(getContext())
                                            .title("Başarılı!")
                                            .content("Konumunuz başarıyla paylaşıldı!")
                                            .contentGravity(GravityEnum.CENTER)
                                            .positiveText("Tamam")
                                            .iconRes(R.drawable.ic_check_circle)
                                            .show();
                                }
                            });
                        }
                    }).show();
            locationManager.removeUpdates(this);
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}
        @Override
        public void onProviderEnabled(String s) {}
        @Override
        public void onProviderDisabled(String s) {}
    };

}
