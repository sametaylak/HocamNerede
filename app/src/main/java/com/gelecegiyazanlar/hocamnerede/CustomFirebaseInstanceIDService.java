package com.gelecegiyazanlar.hocamnerede;

import android.util.Log;

import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class CustomFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (FirebaseHelper.getCurrentUser() != null)
            sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: " + token);
        FirebaseHelper.updateUserFCMToken(token);
    }

}
