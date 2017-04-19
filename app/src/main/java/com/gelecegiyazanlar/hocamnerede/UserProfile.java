package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UserProfile extends Fragment {




    public UserProfile () {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        return mView;
    }





}
