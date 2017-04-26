package com.turkiyedenemeleri.app;

import android.app.Application;

import com.onesignal.OneSignal;
import com.turkiyedenemeleri.model.User;

/**
 * Created by safakesberk on 22/04/2017.
 */

public class MyApp extends Application {
    public static String loggedUserId = "";
    public static User loggedUser ;

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        OneSignal.startInit(this).init();
        OneSignal.idsAvailable((userId, registrationId) -> loggedUserId = userId);


    }

    public static synchronized MyApp getInstance() {
        return instance;
    }

}
