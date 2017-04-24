package com.turkiyedenemeleri.app;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by safakesberk on 22/04/2017.
 */

public class MyApp extends Application {
    public static String loggedUserId=null;
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        OneSignal.startInit(this).init();
        OneSignal.idsAvailable((userId, registrationId) -> loggedUserId=userId);
    }
    public static synchronized MyApp getInstance() {
        return instance;
    }

}
