package com.turkiyedenemeleri;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by safakesberk on 22/04/2017.
 */

public class MyApp extends Application {
    public static String loggedUserId=null;

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
        OneSignal.idsAvailable((userId, registrationId) -> loggedUserId=userId);
    }
}
