package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    public UserProfile () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.userSettingSave)
    public void userSettingSave() {

    }

}
