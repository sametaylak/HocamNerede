package com.gelecegiyazanlar.hocamnerede;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gelecegiyazanlar.hocamnerede.Model.LocationPost;
import com.gelecegiyazanlar.hocamnerede.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Timeline extends Fragment {

    private LocationManager locationManager;
    private MaterialDialog locationProgress;

    public Timeline(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
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
                                    LocationPost post = new LocationPost(
                                            FirebaseHelper.getCurrentUser().getUid(),
                                            location.getLatitude(),
                                            location.getLongitude(),
                                            input.toString(),
                                            user.getUniversity()
                                    );
                                    FirebaseHelper.saveLocationPost(post);
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
