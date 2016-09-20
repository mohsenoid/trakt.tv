package com.mirhoseini.trakttv.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.trakttv.core.util.Constants;

import timber.log.Timber;

/**
 * Created by Mohsen on 20/09/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Timber.d("Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        // TODO: Implement this method to send any registration to your app's servers.
    }

}
