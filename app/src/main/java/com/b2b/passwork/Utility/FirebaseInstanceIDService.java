package com.b2b.passwork.Utility;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;


public class FirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.e("TOKEN", mToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}