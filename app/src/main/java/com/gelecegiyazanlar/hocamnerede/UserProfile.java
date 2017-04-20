package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfile extends Fragment {
    @BindView(R.id.userSettingAvatar) CircleImageView userSettingAvatar;
    @BindView(R.id.userSettingFullName) EditText userSettingFullName;
    @BindView(R.id.userSettingMail) EditText userSettingMail;
    @BindView(R.id.userSettingOldPassword) EditText userSettingOldPassword;
    @BindView(R.id.userSettingNewPassword) EditText userSettingNewPassword;

    private User user;

    public UserProfile () { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        FirebaseFactory.getFirebaseUserDetail(new FirebaseFactory.FirebaseCallback() {
            @Override
            public void onSuccess(Object result) {
                user = (User) result;
                userSettingFullName.setText(user.getFullname());
                userSettingMail.setText(user.getMail());
            }
        });

        return view;
    }

    @OnClick(R.id.userSettingSave)
    public void userSettingSave() {
        if(!userSettingMail.getText().toString().equals(user.getMail())) {
            FirebaseFactory.updateFirebaseUserMail(userSettingMail.getText().toString(),
                    new FirebaseFactory.FirebaseCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            Toast.makeText(getActivity(), "Mail değiştirildi.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

}
