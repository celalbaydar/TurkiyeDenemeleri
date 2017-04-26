package com.turkiyedenemeleri.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.turkiyedenemeleri.app.MyApp;

/**
 * Created by codeest on 16/8/31.
 */

public class SharedPreferenceUtil {

    public static final String SP_USER_PROFİL = "user_profil";
    public static final String SP_USER = "user";

    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    public static SharedPreferences getAppSp() {
        return MyApp.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isUserFillProfil() {
        return getAppSp().getBoolean(SP_USER_PROFİL, false);
    }

    public static void setUserFillProfil(boolean isFill) {
        getAppSp().edit().putBoolean(SP_USER_PROFİL, isFill).apply();
    }

    public static String getLoggedUser() {
        return getAppSp().getString(SP_USER, null);
    }

    public static void setLoggedUser(String user) {
        getAppSp().edit().putString(SP_USER, user).apply();
    }
}
